package com.qarepo.rabbitmq.message;

import com.qarepo.rabbitmq.config.AMQPConfig;
import com.qarepo.rabbitmq.service.BannerRPCService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;


public class MessageConsumer {
    private static final Logger LOGGER = LogManager.getLogger(MessageConsumer.class);
    private StringWriter sw = new StringWriter();

    public MessageConsumer() {
    }

    public void receive(Channel channel, String queueName) {
        try {
            channel.basicQos(1);
            LOGGER.info(" [*] Waiting for messages...");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                LOGGER.info(" [x] Received [" + message + "]");
                String response = "";
                try {
                    String endpoint = "http://localhost:8080/runBannerTests";
                    response = BannerRPCService.callBannerTestService(message, endpoint)
                                               .toString();
                    LOGGER.info(" [*] RESPONSE=" + response);
                } finally {
                    LOGGER.info(" [*] Done");
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                }
            };
            channel.basicConsume(queueName, false, deliverCallback, consumerTag -> {
            });
        } catch (IOException e) {
            e.printStackTrace(new PrintWriter(sw));
            LOGGER.error("Exception: " + sw.toString());
        }
    }
}
