package com.qarepo.rabbitmq.api;

import com.qarepo.rabbitmq.config.MessageConfig;
import com.qarepo.rabbitmq.messages.MessageListenerFactory;
import com.qarepo.rabbitmq.messages.MessageSender;
import com.qarepo.rabbitmq.model.BannerHTML;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Duration;

@RestController
@RequestMapping(path = "/rabbitmq", produces = "application/json")
public class BannerController {
    private static final Logger LOGGER = LogManager.getLogger(BannerController.class);

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    MessageListenerFactory messageListenerFactory;

    private BannerHTML bannerHTML;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public BannerController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public BannerHTML getBannerHTML() {
        return bannerHTML;
    }

    public void setBannerHTML(BannerHTML bannerHTML) {
        this.bannerHTML = bannerHTML;
    }

    @RequestMapping(path = "/run", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendMessage(@RequestBody BannerHTML banners) {
         // Sending to Message Queue
        MessageSender msg = new MessageSender();
        try {
            setBannerHTML(banners);
            msg.send(rabbitTemplate, bannerHTML);
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            LOGGER.error("Exception occurred while sending message to the queue. Exception= {}", sw.toString());
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<BannerHTML>(banners, HttpStatus.OK);
    }
}
