package com.qarepo.rabbitmq.messages.config;

import com.qarepo.rabbitmq.messages.service.BannerMessagePublisher;
import com.qarepo.rabbitmq.messages.service.BannerMessageSubscriber;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

@Profile({"config", "hello-world"})
@Configuration
public class RabbitMQConfig implements RabbitListenerConfigurer {
    public static final String BANNERS_QUEUE = "banners-queue";
    public static final String BANNERS_EXCHANGE = "banners-exchange";
    public static final String BANNERS_QUEUE_DEAD = "dead-banner-queue";

    @Bean
    public Queue bannersQueue() {
        return QueueBuilder.durable(BANNERS_QUEUE)
                           .withArgument("x-dead-letter-exchange", "")
                           .withArgument("x-dead-letter-routing-key", BANNERS_QUEUE_DEAD)
                           .withArgument("x-message-ttl", 15000)
                           .build();
    }

    @Profile("receiver")
    @Bean
    public BannerMessageSubscriber receiver() {
        return new BannerMessageSubscriber();
    }

    @Profile("sender")
    @Bean
    public BannerMessagePublisher sender() {
        return new BannerMessagePublisher();
    }

    @Bean
    Exchange bannersExchange() {
        return ExchangeBuilder.topicExchange(BANNERS_EXCHANGE).build();
    }

    @Bean
    Binding binding(Queue bannerQueue, TopicExchange bannerExchange) {
        return BindingBuilder.bind(bannerQueue).to(bannerExchange).with(BANNERS_QUEUE);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }

    @Bean
    MessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory messageHandlerMethodFactory = new DefaultMessageHandlerMethodFactory();
        messageHandlerMethodFactory.setMessageConverter(consumerJackson2MessageConverter());
        return messageHandlerMethodFactory;
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }
}
