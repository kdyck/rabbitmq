package com.qarepo.rabbitmq.springboot;


import com.qarepo.rabbitmq.springampq.QueueMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class BannerMessagePublisher {
    private static final Logger LOGGER = LogManager.getLogger(BannerMessagePublisher.class);

    @Autowired
    private Queue queue;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send() {
        LOGGER.info("Sending Banners...");
        QueueMessage banners = new QueueMessage();
        this.rabbitTemplate.setMessageConverter(new RabbitConfig().producerJackson2MessageConverter());
        this.rabbitTemplate.convertAndSend(RabbitConfig.BANNERS_EXCHANGE, RabbitConfig.BANNERS_QUEUE, banners);
        LOGGER.info("[x] Sent [ {} ]", banners);
    }
}
