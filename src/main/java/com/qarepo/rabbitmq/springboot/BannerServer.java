package com.qarepo.rabbitmq.springboot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.util.StopWatch;

@RabbitListener(queues = RabbitRpcConfig.BANNERS_QUEUE)
public class BannerServer {
    private static final Logger LOGGER = LogManager.getLogger(BannerServer.class);


    @RabbitHandler
    public void receive(BannerHTMLMessage banners) {
        final int instance = 0;
        StopWatch watch = new StopWatch();
        watch.start();
        LOGGER.info("instance " + instance +
                " [x] Received '" + banners + "'");
        connectToExecuteBannerEndpoint(banners);
        watch.stop();
        LOGGER.info("instance " + instance +
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
}
