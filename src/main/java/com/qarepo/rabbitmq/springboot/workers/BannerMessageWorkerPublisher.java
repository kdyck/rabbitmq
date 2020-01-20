package com.qarepo.rabbitmq.springboot.workers;

import com.qarepo.rabbitmq.springboot.BannerHTMLMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.atomic.AtomicInteger;

/*public class BannerMessageWorkerPublisher {
    private static final Logger LOGGER = LogManager.getLogger(BannerMessageWorkerPublisher.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Queue queue;

    AtomicInteger dots = new AtomicInteger(0);
    AtomicInteger count = new AtomicInteger(0);

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send() {
        BannerHTMLMessage banners = new BannerHTMLMessage();
        rabbitTemplate.setMessageConverter(new RabbitWrkrConfig().producerJackson2MessageConverter());
        rabbitTemplate.convertAndSend(RabbitWrkrConfig.BANNERS_QUEUE, banners);
        LOGGER.info(" [x] Sent '" +  banners.toString() + "'");
    }
}*/
