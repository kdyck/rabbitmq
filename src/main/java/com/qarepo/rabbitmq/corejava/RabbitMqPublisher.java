package com.qarepo.rabbitmq.corejava;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

public class RabbitMqPublisher {
    private static final Logger LOGGER = LogManager.getLogger(RabbitMqPublisher.class);
    private StringWriter sw = new StringWriter();

    public RabbitMqPublisher() {
    }

    void publish(Connection connection, String exchangeName, String queueName, String msg) {
        LOGGER.info("Sending Message");
        try (Channel channel = connection.createChannel()) {
            channel.queueDeclare(queueName, true, false, false, null);
            channel.basicPublish(exchangeName, queueName,
                    MessageProperties.PERSISTENT_TEXT_PLAIN,
                    msg.getBytes(StandardCharsets.UTF_8));
            LOGGER.info("[x] Sent {" + queueName + "}");
        } catch (Exception e) {
            e.printStackTrace(new PrintWriter(sw));
            LOGGER.log(Level.ERROR, "Exception: " + sw.toString());
        } finally {
            try {
                LOGGER.info("Closing RabbitMQ Connection...");
                connection.close();
            } catch (IOException e) {
                e.printStackTrace(new PrintWriter(sw));
                LOGGER.log(Level.ERROR, "Exception: " + sw.toString());
            }
        }
    }
}
