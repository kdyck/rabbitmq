package com.qarepo.rabbitmq.messages;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

@Component
public class RabbitMqSubscriber {
    private static final Logger LOGGER = LogManager.getLogger(RabbitMqPublisher.class);
    private StringWriter sw = new StringWriter();

    public RabbitMqSubscriber() {
    }

    public void subscribe(Connection connection, String queueName) {
        try {
            Channel channel = connection.createChannel();
            channel.queueDeclare(queueName, true, false, false, null);
            LOGGER.info(" [*] Waiting for messages. To exit press CTRL+C");
            channel.basicQos(1);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                LOGGER.info(" [x] Received [" + message + "]");
                try {
                    doWork(message);
                } finally {
                    LOGGER.info(" [x] Done");
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                }
            };
            channel.basicConsume(queueName, false, deliverCallback, consumerTag -> {
            });
        } catch (IOException e) {
            e.printStackTrace(new PrintWriter(sw));
            LOGGER.log(Level.ERROR, "Exception: " + sw.toString());
        }
    }

    private void doWork(String message) {
        try {
            for (char ch : message.toCharArray()) {
                if (ch == '.')
                    Thread.sleep(1000);
            }
        } catch (InterruptedException _ignored) {
            Thread.currentThread().interrupt();
        }
    }
}
