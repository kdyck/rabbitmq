package com.qarepo.rabbitmq;

import com.qarepo.rabbitmq.config.AMQPConfig;
import com.qarepo.rabbitmq.message.MessagePublisher;
import com.rabbitmq.client.Channel;

public class App {

    public static void main(String[] args) throws Exception {
        final String QUEUE_NAME = "banners_rpc_queue";
        String json = "{\n" +
                "    \"jobNumber\": \"KAY2890928\",\n" +
                "    \"jobName\": \"Gifting Valentines Day Base Sale Personalize\",\n" +
                "    \"previewURL\": \"http://zpreview.ztrac.com/clients/196036cf135fba392e12e6b1c622915d\"\n" +
                "}";
        MessagePublisher publisher = new MessagePublisher();
        Channel channel = AMQPConfig.connect();
        try {
            for (int i = 0; i <= 4; i++) {
                publisher.send(channel, "", QUEUE_NAME, json);
            }
        } finally {
            AMQPConfig.close(channel);
        }
        //  MessageConsumer consumer = new MessageConsumer();
        //  consumer.receive(QUEUE_NAME);
    }
}
