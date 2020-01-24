package com.qarepo.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AMQPApplication {

	public static void main(String[] args) {
		SpringApplication.run(AMQPApplication.class, args);
	}
}
