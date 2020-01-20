package com.qarepo.rabbitmq.springboot.workers;

import com.qarepo.rabbitmq.springboot.BannerHTMLMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.util.StopWatch;

/*@RabbitListener(queues = RabbitWrkrConfig.BANNERS_QUEUE)
public class BannerMessageWorkerSubscriber {
    private static final Logger LOGGER = LogManager.getLogger(BannerMessageWorkerSubscriber.class);

    private final int instance;

    public BannerMessageWorkerSubscriber(int instance) {
        this.instance = instance;
    }

    @RabbitHandler
    public void receive(BannerHTMLMessage banners) {
        StopWatch watch = new StopWatch();
        watch.start();
        LOGGER.info("instance " + this.instance +
                " [x] Received '" + banners + "'");

        connectToExecuteBannerEndpoint(banners);
        watch.stop();
        LOGGER.info("instance " + this.instance +
                " [x] Done in " + watch.getTotalTimeSeconds() + "s");
    }

    private void connectToExecuteBannerEndpoint(BannerHTMLMessage banners) {
        for (byte banner : banners.getJobName().getBytes()) {
            try{
                if (banner == '.'){
                    Thread.sleep(1000);
                }
            } catch (InterruptedException ignored) {
            }
        }
    }
}*/
