package com.qarepo.rabbitmq.message;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

public class MessageSender {
    private static final Logger LOGGER = LogManager.getLogger(MessageSender.class);
    private StringWriter sw = new StringWriter();

    public MessageSender() {
    }

    public void send(Channel channel, String exchangeName, String queueName, String msg) {
        try {
            channel.queueDeclare(queueName, true, false, false, null);
            channel.basicPublish(exchangeName, queueName,
                    MessageProperties.PERSISTENT_TEXT_PLAIN,
                    msg.getBytes(StandardCharsets.UTF_8));
            LOGGER.info(" [*] Sending Message [" + msg + "]");
        } catch (Exception e) {
            e.printStackTrace(new PrintWriter(sw));
            LOGGER.log(Level.ERROR, "Exception: " + sw.toString());
        }
    }
}
