package com.qarepo.rabbitmq.config;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class AMQPConfig {
    private static final Logger LOGGER = LogManager.getLogger(AMQPConfig.class);

    public static Channel connect() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        LOGGER.info(" [*] Starting new AMQP Connection & Creating channel.");
        Connection connection = factory.newConnection();
        return connection.createChannel();
    }

    public static void close(Channel channel) throws Exception {
        LOGGER.info(" [*] Closing AMQP Connection.");
        channel.close();
        channel.getConnection().close();
    }
}
