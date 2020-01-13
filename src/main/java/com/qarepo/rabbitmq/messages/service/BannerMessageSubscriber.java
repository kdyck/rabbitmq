package com.qarepo.rabbitmq.messages.service;

import com.qarepo.rabbitmq.messages.config.RabbitMQConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@RabbitListener(queues = RabbitMQConfig.BANNERS_QUEUE)
public class BannerMessageSubscriber {
    private static final Logger LOGGER = LogManager.getLogger(BannerMessageSubscriber.class);

    @RabbitHandler
    public void receive(Message message) {
        LOGGER.info("Receiving Banners: {} from queue.", message);
        LOGGER.info("[x] Received [ " + message + " ]");
    }
}
