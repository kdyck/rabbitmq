package com.qarepo.rabbitmq.springampq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

// @Profile({"config","hello-world"})
// @Configuration
public class Config {

    @Bean
    public Queue hello() {
        return new Queue("hello");
    }

    @Profile("receiver_ampq")
    @Bean
    public TestSubscriber receiver_ampq() {
        return new TestSubscriber();
    }

    @Profile("sender_ampq")
    @Bean
    public TestPublisher sender_ampq() {
        return new TestPublisher();
    }
 }

