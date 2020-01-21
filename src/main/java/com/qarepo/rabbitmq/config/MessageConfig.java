package com.qarepo.rabbitmq.config;

import com.qarepo.rabbitmq.messages.MessageSender;
import com.qarepo.rabbitmq.messages.MessageListener;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
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

@Profile({"banners"})
@Configuration
public class MessageConfig implements RabbitListenerConfigurer {
    public static final String BANNERS_QUEUE = "banners.queue";
    public static final String BANNERS_EXCHANGE = "banners.exchange";
    public static final String BANNERS_ROUTING_KEY = "banners.*";

    @Profile("receiver")
    @Bean
    public MessageListener receiver() {
            return new MessageListener();
        }

    @Profile("sender")
    @Bean
    public MessageSender sender() {
        return new MessageSender();
    }

    @Bean
    Exchange bannersExchange() {
        return ExchangeBuilder.topicExchange(BANNERS_EXCHANGE)
                              .build();
    }

    @Bean
    public Queue bannersQueue() {
        return QueueBuilder.durable(BANNERS_QUEUE)
                           .ttl(1000)
                           .expires(200_000)
                           .maxLength(42)
                           .maxLengthBytes(10_000)
                           .overflow(QueueBuilder.Overflow.rejectPublish)
                           .deadLetterExchange("dlx")
                           .deadLetterRoutingKey("dlrk")
                           .maxPriority(4)
                           .lazy()
                           .singleActiveConsumer()
                           .build();
    }

    @Bean
    Binding binding(Queue bannerQueue, TopicExchange bannerExchange) {
        return BindingBuilder.bind(bannerQueue)
                             .to(bannerExchange)
                             .with(BANNERS_ROUTING_KEY);
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
