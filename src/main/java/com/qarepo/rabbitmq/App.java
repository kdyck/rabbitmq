package com.qarepo.rabbitmq;


import com.qarepo.rabbitmq.message.MessageReceiver;
import com.qarepo.rabbitmq.message.MessageSender;

public class App {

    public static void main(String[] args) throws Exception {
        final String QUEUE_NAME = "banners_rpc_queue";
        String json = "{\n" +
                "    \"jobNumber\": \"KAY2890928\",\n" +
                "    \"jobName\": \"Gifting Valentines Day Base Sale Personalize\",\n" +
                "    \"previewURL\": \"http://zpreview.ztrac.com/clients/196036cf135fba392e12e6b1c622915d\"\n" +
                "}";
        MessageSender sender = new MessageSender();
    /*    Channel channel = AMQPConfig.connect();
        try {
            for (int i = 0; i <= 4; i++) {
                sender.send(channel, "", QUEUE_NAME, json);
            }
        } finally {
            AMQPConfig.close(channel);
        }*/
        MessageReceiver receiver = new MessageReceiver();
        receiver.receive(QUEUE_NAME);
        // AMQPConfig.close(channel);
    }
}
