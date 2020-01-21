package com.qarepo.rabbitmq;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class AMQPApplication {

	@Profile("usage_message")
	@Bean
	public CommandLineRunner usage() {
		return args -> {
			System.out.println("This app uses Spring Profiles to control its behavior.\n");
			System.out.println("Sample usage: java -jar rabbitmq-0.0.1-SNAPSHOT.jar" +
					" --spring.profiles.active=banners, sender");
		};
	}

	@Profile("!usage_message")
	@Bean
	public CommandLineRunner commandLineRunner() {
		return new RabbitCMDRunner();
	}

	public static void main(String[] args) {
		SpringApplication.run(AMQPApplication.class, args);
	}
}
