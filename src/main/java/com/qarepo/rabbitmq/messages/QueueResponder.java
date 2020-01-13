package com.qarepo.rabbitmq.messages;

public interface QueueResponder {
    void respond(String response);
}
