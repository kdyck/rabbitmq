package com.qarepo.rabbitmq.api;

import com.qarepo.rabbitmq.messages.MessageSender;
import com.qarepo.rabbitmq.model.BannerHTML;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.PrintWriter;
import java.io.StringWriter;

@RestController
@RequestMapping(path = "/", produces = "application/json")
public class BannerController {
    private static final Logger LOGGER = LogManager.getLogger(BannerController.class);

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public BannerController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RequestMapping(path = "/run", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendMessage(@RequestBody BannerHTML banners) {
        /* Sending to Message Queue */
        MessageSender msg = new MessageSender();
        try {
            msg.setBannerHTML(banners);
            msg.send(rabbitTemplate);

        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            LOGGER.error("Exception occurred while sending message to the queue. Exception= {}",sw.toString());
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<BannerHTML>(HttpStatus.OK);
    }
}
