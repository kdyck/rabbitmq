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

    @Autowired
    MessageConfig config;

    @Autowired
    private Queue queue;

   // @Scheduled
    public void send(final RabbitTemplate rabbitTemplate, final BannerHTML bannerHTML) {
        LOGGER.info(" [x] Sending message to the queue using routingKey= {}. Message= {}", MessageConfig.BANNERS_ROUTING_KEY, bannerHTML);
        rabbitTemplate.setMessageConverter(new MessageConfig().producerJackson2MessageConverter());
        rabbitTemplate.convertAndSend(MessageConfig.BANNERS_EXCHANGE, MessageConfig.BANNERS_ROUTING_KEY, bannerHTML);
        LOGGER.info(" [x] The message has been sent to the queue.");
    }
}
