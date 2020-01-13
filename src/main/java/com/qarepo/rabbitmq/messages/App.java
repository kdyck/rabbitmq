package com.qarepo.rabbitmq.messages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qarepo.rabbitmq.messages.model.QueueMessage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class App {

    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        QueueMessage message = new QueueMessage("AALB2986152", "FAKEJOB 1361570770 HTML5 Banners"
                , "");

        String jsonMsg = mapper.writeValueAsString(message);
        System.out.println(jsonMsg);

        RabbitMqConnection connection = new RabbitMqConnection();
        RabbitMqPublisher publisher = new RabbitMqPublisher();
        RabbitMqSubscriber subscriber = new RabbitMqSubscriber();
        List<String> queueName = new ArrayList<>();

        for (int i = 0; i <= 2; i++) {
            queueName.add(message.getJobNumber() + "-" + i);
            publisher.publish(connection.getConnection(), "", queueName.get(i)
                    , jsonMsg + "\n{\"date/time\": " + "\"" + LocalDateTime.now() + "\"}");
        }
        subscriber.subscribe(connection.getConnection(), queueName.get(ThreadLocalRandom.current()
                                                                                        .nextInt(1, queueName.size())));
    }
}
