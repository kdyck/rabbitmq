package com.qarepo.rabbitmq;

import com.qarepo.rabbitmq.config.AMQPConfig;
import com.qarepo.rabbitmq.message.MessageReceiver;
import com.rabbitmq.client.Channel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@SpringBootApplication
public class RabbitMQApplication {
	static final String QUEUE_NAME = "banners_rpc_queue";

	public static void main(String[] args) throws IOException, TimeoutException {
		SpringApplication.run(RabbitMQApplication.class, args);
		Channel channel = AMQPConfig.connect();
		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
		// Banner QUEUE Consumer
		MessageReceiver receiver = new MessageReceiver();
		receiver.receive(QUEUE_NAME);
	}
}
