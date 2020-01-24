package com.qarepo.rabbitmq.messages;

import com.qarepo.rabbitmq.config.MessageConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.List;
import java.util.Map;

import static com.qarepo.rabbitmq.config.MessageConfig.BANNERS_WRK_QUEUE;
import static com.qarepo.rabbitmq.config.MessageConfig.PARKING_QUEUE;

@Component
public class MessageListenerFactory {
    private static final Logger LOGGER = LogManager.getLogger(MessageListenerFactory.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = BANNERS_WRK_QUEUE)
    public void receive(Message message) throws Exception {
        StopWatch watch = new StopWatch();
        watch.start();
        LOGGER.info(" [x] Received message: {} from banners worker queue.", message);
        if (hasExceededRetryCount(message)) {
            putIntoParkingLot(message);
            return;
        }
        watch.stop();
        LOGGER.info(" [x] Done in " + watch.getTotalTimeSeconds() + "s");
        throw new Exception(" An error occurred while reading message");
    }

    private boolean hasExceededRetryCount(Message message) {
        List<Map<String, ?>> x_Death = message.getMessageProperties()
                                              .getXDeathHeader();
        if (x_Death != null && x_Death.size() >= 1) {
            Long count = (Long) x_Death.get(0)
                                       .get("count");
            return count >= 3;
        }
        return false;
    }

    private void putIntoParkingLot(Message failedMessage) {
        LOGGER.info("Retried exceeded, putting message {} into parking lot queue.", failedMessage);
        this.rabbitTemplate.send(PARKING_QUEUE, failedMessage);
    }
}
