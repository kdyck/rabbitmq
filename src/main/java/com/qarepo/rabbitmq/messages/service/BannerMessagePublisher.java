package com.qarepo.rabbitmq.messages.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qarepo.rabbitmq.messages.config.RabbitMQConfig;
import com.qarepo.rabbitmq.messages.model.QueueMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

public class BannerMessagePublisher {
    private static final Logger LOGGER = LogManager.getLogger(BannerMessagePublisher.class);
    private StringWriter sw = new StringWriter();

    @Autowired
    private Queue queue;

    @Autowired
    private RabbitTemplate template;

    @Bean
    private ObjectMapper objMapper() {
     return new ObjectMapper();
    }

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send() {
        LOGGER.info("Sending Banners...");
        QueueMessage banners = new QueueMessage("AALB2986152", "FAKEJOB 1361570770 HTML5 Banners"
                , "http://zpreview.ztrac.com/clients/583f191395dbd78ca1a2dae63a87acdc");
        try {
            String bannerJson = objMapper().writeValueAsString(banners);
            Message message = MessageBuilder.withBody(bannerJson.getBytes(StandardCharsets.UTF_8))
                                            .setReplyTo("importReply")
                                            .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                                            .build();
            this.template.convertAndSend(RabbitMQConfig.BANNERS_EXCHANGE, RabbitMQConfig.BANNERS_QUEUE, message);
            LOGGER.info("[x] Sent [ {} ]", message);
        } catch (JsonProcessingException e) {
            e.printStackTrace(new PrintWriter(sw));
            LOGGER.error("Exception: " + sw.toString());
        }
    }
}
