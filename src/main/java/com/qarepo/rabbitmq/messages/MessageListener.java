package com.qarepo.rabbitmq.messages;

import com.qarepo.rabbitmq.model.BannerHTML;
import com.qarepo.rabbitmq.config.MessageConfig;
import com.qarepo.rabbitmq.model.BannerResponse;
import com.qarepo.rabbitmq.service.BannerClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.util.StopWatch;


public class MessageListener {
    private static final Logger LOGGER = LogManager.getLogger(MessageListener.class);

    @RabbitListener(queues = MessageConfig.BANNERS_QUEUE)
    public void receive(final BannerHTML banners) {
        StopWatch watch = new StopWatch();
        watch.start();
        LOGGER.info("  [x] Received message: {} from banners queue.", banners);
        //rabbitTemplate.receive(MessageConfig.BANNERS_EXCHANGE);
        watch.stop();
        LOGGER.info(" [x] The message {} has been received from the queue." , banners.getJobName()
                + " [x] Done in " + watch.getTotalTimeSeconds() + "s");
    }
}
