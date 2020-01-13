package com.qarepo.rabbitmq.messages.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/*@Profile({"config","hello-world"})
@Configuration
public class Config {

    @Bean
    public Queue hello() {
        return new Queue("hello");
    }

    @Profile("receiver")
    @Bean
    public TestSubscriber receiver() {
        return new TestSubscriber();
    }

    @Profile("sender")
    @Bean
    public TestPublisher sender() {
        return new TestPublisher();
    }
 }*/

