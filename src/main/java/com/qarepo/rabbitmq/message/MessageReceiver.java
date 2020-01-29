package com.qarepo.rabbitmq.message;

import com.qarepo.rabbitmq.config.AMQPConfig;
import com.qarepo.rabbitmq.service.BannerRPCService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;


public class MessageReceiver {
    private static final Logger LOGGER = LogManager.getLogger(MessageReceiver.class);
    private StringWriter sw = new StringWriter();
    private static final String ENDPOINT = "http://localhost:8080/runBannerTests";

    public MessageReceiver() {
    }

    public void receive(String queueName) {
        try {
            Channel channel = AMQPConfig.connect();
            channel.basicQos(1);
            LOGGER.info(" [*] Waiting for messages...");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                LOGGER.info(" [x] Received [" + message + "]");
                String response = "";
                try {
                    response = BannerRPCService.executeBannerTestService(message, ENDPOINT)
                                               .toString();
                    LOGGER.info(" [*] RESPONSE=" + response);
                } finally {
                    LOGGER.info(" [*] Done");
                    channel.basicAck(delivery.getEnvelope()
                                             .getDeliveryTag(), false);
                }
            };
            channel.basicConsume(queueName, false, deliverCallback, consumerTag -> {
            });
        } catch (IOException | TimeoutException e) {
            e.printStackTrace(new PrintWriter(sw));
            LOGGER.log(Level.ERROR, "Exception: " + sw.toString());
        }
    }
}
