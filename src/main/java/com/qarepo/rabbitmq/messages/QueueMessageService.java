package com.qarepo.rabbitmq.messages;


import com.qarepo.rabbitmq.messages.model.QueueMessage;

public interface QueueMessageService {
    public QueueMessage save(QueueMessage message);

    public QueueMessage findById(int id);
}
