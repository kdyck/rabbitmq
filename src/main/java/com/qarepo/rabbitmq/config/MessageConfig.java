package com.qarepo.rabbitmq.config;

import com.qarepo.rabbitmq.messages.MessageListenerFactory;
import com.qarepo.rabbitmq.messages.MessageSender;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

@Configuration
public class MessageConfig implements RabbitListenerConfigurer {
    public static final String BANNERS_EXCHANGE = "banners-exchange";
    public static final String BANNERS_WRK_QUEUE = "banners-workerQueue";

    public static final String WAIT_QUEUE = BANNERS_WRK_QUEUE + ".wait";
    public static final String PARKING_QUEUE = BANNERS_WRK_QUEUE + ".parkingLot";

    public static final String BANNERS_ROUTING_KEY = "banners-routingKey";

    @Bean
    public MessageSender sender() {
        return new MessageSender();
    }

    @Bean
    public MessageListenerFactory receiver() {
        return new MessageListenerFactory();
    }

    @Bean
    @Primary
    public Queue queue() {
        return QueueBuilder.durable(BANNERS_WRK_QUEUE)
                           .deadLetterExchange(BANNERS_EXCHANGE)
                           .deadLetterRoutingKey(WAIT_QUEUE)
                           .build();
    }

    @Bean
    Queue waitQueue() {
        return QueueBuilder.durable(WAIT_QUEUE)
                           .deadLetterExchange(BANNERS_EXCHANGE)
                           .deadLetterRoutingKey(BANNERS_ROUTING_KEY)
                           .ttl(10000)
                           .build();
    }

    @Bean
    Queue parkingLotQueue () {
        return new Queue(PARKING_QUEUE);
    }

    @Bean
    Exchange exchange() {
        return ExchangeBuilder.directExchange(BANNERS_EXCHANGE)
                              .build();
    }

    @Bean
    Binding binding(Queue bannerQueue, DirectExchange exchange) {
        return BindingBuilder.bind(bannerQueue)
                             .to(exchange)
                             .with(BANNERS_WRK_QUEUE);
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
