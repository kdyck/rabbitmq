package com.qarepo.rabbitmq.messages;

import com.qarepo.rabbitmq.config.MessageConfig;
import com.qarepo.rabbitmq.model.BannerHTML;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class MessageSender {
    private static final Logger LOGGER = LogManager.getLogger(MessageSender.class);
    private BannerHTML bannerHTML;

    @Autowired
    private Queue queue;

    public BannerHTML getBannerHTML() {
        return bannerHTML;
    }

    public void setBannerHTML(BannerHTML bannerHTML) {
        this.bannerHTML = bannerHTML;
    }

   //  @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send(RabbitTemplate rabbitTemplate) {
        LOGGER.info(" [x] Sending message to the queue using routingKey= {}. Message= {}", "banners.*", bannerHTML);
        rabbitTemplate.convertSendAndReceive(MessageConfig.BANNERS_EXCHANGE, MessageConfig.BANNERS_ROUTING_KEY, bannerHTML);
        LOGGER.info(" [x] The message has been sent to the queue.");

    }
}
