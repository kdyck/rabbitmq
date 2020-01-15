package com.qarepo.rabbitmq.springboot;

import com.qarepo.rabbitmq.springampq.QueueMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@RabbitListener(queues = RabbitConfig.BANNERS_QUEUE)
public class BannerMessageSubscriber {
    private static final Logger LOGGER = LogManager.getLogger(BannerMessageSubscriber.class);

    @RabbitHandler
    public void receive(QueueMessage banner) {
        LOGGER.info("Receiving Banners: {} from queue.", banner.getJobNumber());
        LOGGER.info("[x] Received [ " + banner.getJobName() + " ]");
    }
}
