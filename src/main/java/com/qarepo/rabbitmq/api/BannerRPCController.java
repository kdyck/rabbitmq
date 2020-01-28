package com.qarepo.rabbitmq.api;

import com.qarepo.rabbitmq.config.AMQPConfig;
import com.qarepo.rabbitmq.message.MessagePublisher;
import com.rabbitmq.client.Channel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/", produces = "application/json")
public class BannerRPCController {
    private static final Logger LOGGER = LogManager.getLogger(BannerRPCController.class);
    final String QUEUE_NAME = "banners_rpc_queue";

    @PostMapping(path = "/queue")
    public String executeBannerTestService(@RequestBody String json) throws Exception {
        MessagePublisher publisher = new MessagePublisher();
        Channel channel = AMQPConfig.connect();
        if (json != null) publisher.send(channel, "", QUEUE_NAME, json);
        AMQPConfig.close(channel);
        return " [*] Sent message to test queue [ " +  json + " ]";
    }
}
