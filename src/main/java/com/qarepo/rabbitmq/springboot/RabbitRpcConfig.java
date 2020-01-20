package com.qarepo.rabbitmq.springboot;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.remoting.service.AmqpInvokerServiceExporter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

@Profile({"config", "banners-rpc"})
@Configuration
public class RabbitRpcConfig implements RabbitListenerConfigurer {
    public static final String BANNERS_QUEUE = "banners.rpc.requests";
    public static final String BANNERS_EXCHANGE = "banners.rpc";
    public static final String BANNERS_ROUTING_KEY = "rpc";

    @Profile("client")
    private static class ClientConfig {

        @Bean
        public DirectExchange exchange() {
            return ExchangeBuilder.directExchange(BANNERS_EXCHANGE).build();
        }

        @Bean
        public BannerClient client() {
            return new BannerClient();
        }
    }

    @Profile("server")
    private static class ServerConfig {

        @Bean
        public Queue queue() {
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
        public DirectExchange exchange() {
            return ExchangeBuilder.directExchange(BANNERS_EXCHANGE)
                                  .build();
        }

        @Bean
        public Binding binding(DirectExchange exchange, Queue queue) {
            return BindingBuilder.bind(queue)
                                 .to(exchange)
                                 .with(BANNERS_ROUTING_KEY);
        }

        @Bean
        public BannerServer server() {
            return new BannerServer();
        }


        @Bean
        AmqpInvokerServiceExporter exporter(BannerHTMLMessage implementation, AmqpTemplate template) {
            AmqpInvokerServiceExporter exporter = new AmqpInvokerServiceExporter();
            exporter.setServiceInterface(BannerHTMLMessage.class);
            exporter.setService(implementation);
            exporter.setAmqpTemplate(template);
            return exporter;
        }

        @Bean
        SimpleMessageListenerContainer listener(ConnectionFactory factory, AmqpInvokerServiceExporter exporter, Queue queue) {
            SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(factory);
            container.setMessageListener(exporter);
            container.setQueueNames(queue.getName());
            return container;
        }
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
