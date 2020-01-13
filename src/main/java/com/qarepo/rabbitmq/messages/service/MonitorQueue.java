package com.qarepo.rabbitmq.messages.service;

public class MonitorQueue {

    private static void monitorReportQueue(String baseUrl ){

        BannerMessagePublisher publisher = new BannerMessagePublisher();
        BannerMessageSubscriber subscriber = new BannerMessageSubscriber();

        String requestsQueue = "requests";
        String responsesQueue = "results";

        publisher.send();


    }
}
