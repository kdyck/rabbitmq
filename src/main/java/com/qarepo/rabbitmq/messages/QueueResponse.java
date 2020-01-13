package com.qarepo.rabbitmq.messages;

import com.qarepo.rabbitmq.messages.service.BannerMessagePublisher;

public class QueueResponse implements QueueResponder {
    private String queueName;
    private String responseUrl;

    public QueueResponse(String queueName, String responseUrl) {
        this.queueName = queueName;
        this.responseUrl = responseUrl;
    }

    public String getResponseUrl() {
        return responseUrl;
    }

    public void setResponseUrl(String responseUrl) {
        this.responseUrl = responseUrl;
    }

    @Override
    public void respond(String response) {
        BannerMessagePublisher publisher = new BannerMessagePublisher();
        publisher.send();
    }
}
