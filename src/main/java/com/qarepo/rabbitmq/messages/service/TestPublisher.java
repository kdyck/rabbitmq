package com.qarepo.rabbitmq.messages.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

/*public class TestPublisher {
    private static final Logger LOGGER = LogManager.getLogger(TestPublisher.class);

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private Queue queue;

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send() {
        LOGGER.info("Sending Banners...");
        String message = "Hello World!";
        this.template.convertAndSend(queue.getName(), message);
        LOGGER.info(" [x] Sent '" + message + "'");
    }
}*/
