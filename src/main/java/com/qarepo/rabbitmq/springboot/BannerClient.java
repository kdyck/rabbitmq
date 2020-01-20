package com.qarepo.rabbitmq.springboot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.atomic.AtomicInteger;

public class BannerClient {
    private static final Logger LOGGER = LogManager.getLogger(BannerClient.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private DirectExchange exchange;

    AtomicInteger dots = new AtomicInteger(0);
    AtomicInteger count = new AtomicInteger(0);

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send() {

        BannerHTMLMessage banners = new BannerHTMLMessage();
        StringBuilder response = BannerClient.getBannerHtmlResponse(banners, "runBannerTests");
        rabbitTemplate.setMessageConverter(new RabbitRpcConfig().producerJackson2MessageConverter());
        rabbitTemplate.convertAndSend(RabbitRpcConfig.BANNERS_QUEUE, response);
        LOGGER.info(" [x] Sent '" + banners.toString() + "'");
    }


    public static StringBuilder getBannerHtmlResponse(BannerHTMLMessage banners, String endPoint) {
        StringBuilder response = null;
        String json = banners.toString();
        URL url;
        HttpURLConnection conn = null;
        try {
            url = new URL("http://localhost:8080/" + endPoint);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Content-Length", Integer.toString(json.getBytes().length));
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            DataOutputStream fos = new DataOutputStream(conn.getOutputStream());
            fos.writeBytes(json);
            fos.flush();
            fos.close();

            InputStream is = conn.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            response = new StringBuilder();
            while ((line = rd.readLine())!= null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();

        } catch (IOException e) {
            LOGGER.error(e.toString());
        }
        return response;
    }

    public static void main(String[] args) {
        BannerClient.getBannerHtmlResponse(new BannerHTMLMessage(), "runBannerTests");
    }
}
