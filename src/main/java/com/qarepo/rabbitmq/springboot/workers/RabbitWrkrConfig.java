package com.qarepo.rabbitmq.springboot.workers;

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

/*@Profile({"config", "banners-workers"})
@Configuration
public class RabbitWrkrConfig implements RabbitListenerConfigurer {
    public static final String BANNERS_QUEUE = "banners-queue-worker";
    public static final String BANNERS_EXCHANGE = "banners-exchange-worker";
    public static final String BANNERS_QUEUE_DEAD = "dead-banner-queue-worker";

    @Bean
    public Queue bannersQueue() {
        return QueueBuilder.durable(BANNERS_QUEUE)
                           .withArgument("x-dead-letter-exchange", "")
                           .withArgument("x-dead-letter-routing-key", BANNERS_QUEUE_DEAD)
                           .withArgument("x-message-ttl", 15000)
                           .build();
    }

    @Profile("receiver-wrkr")
    public static class ReceiverConfig {

        @Bean
        public BannerMessageWorkerSubscriber receiver1() {
            return new BannerMessageWorkerSubscriber(1);
        }

        @Bean
        public BannerMessageWorkerSubscriber receiver2() {
            return new BannerMessageWorkerSubscriber(2);
        }
    }

    @Profile("sender-wrkr")
    @Bean
    public BannerMessageWorkerPublisher sender1() {
        return new BannerMessageWorkerPublisher();
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
}*/
