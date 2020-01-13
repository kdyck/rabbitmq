package com.qarepo.rabbitmq.messages;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;

public class RabbitMqConnection {
    private static final Logger LOGGER = LogManager.getLogger(RabbitMqConnection.class);
    private StringWriter sw = new StringWriter();
    private String host;
    private int port;
    private String virtualHost;
    private int heartbeat;
    private String userName;
    private String password;

    public RabbitMqConnection() {
        this.host = "localhost";
        this.port = 5672;
        this.virtualHost = "virtualHost";
        this.heartbeat = 60;
        this.userName = "guest";
        this.password = "guest";
    }

    public RabbitMqConnection(String host, int port, String virtualHost, int heartbeat, String userName, String password) {
        this.host = host;
        this.port = port;
        this.virtualHost = virtualHost;
        this.heartbeat = heartbeat;
        this.userName = userName;
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getVirtualHost() {
        return virtualHost;
    }

    public void setVirtualHost(String virtualHost) {
        this.virtualHost = virtualHost;
    }

    public int getHeartbeat() {
        return heartbeat;
    }

    public void setHeartbeat(int heartbeat) {
        this.heartbeat = heartbeat;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    Connection getConnection() {
        LOGGER.info("Getting RabbitMQ Connection...");
        RabbitMqSettings settings = new RabbitMqSettings(getHost(), getPort(), getVirtualHost(),
                getHeartbeat(), getUserName(), getPassword());
        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = null;
        try {
            connection = factory.newConnection();
            factory.setHost(settings.getHost());
            factory.setPort(settings.getPort());
            factory.setVirtualHost(settings.getVirtualHost());
            factory.setRequestedHeartbeat(settings.getHeartbeat());
            factory.setUsername(settings.getUserName());
            factory.setPassword(settings.getPassword());
            factory.useSslProtocol();
        } catch (Exception e) {
            e.printStackTrace(new PrintWriter(sw));
            LOGGER.log(Level.ERROR, "Exception: " + sw.toString());
        }
        return connection;
    }
}
