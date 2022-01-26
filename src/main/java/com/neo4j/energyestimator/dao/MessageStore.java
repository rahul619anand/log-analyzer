package com.neo4j.energyestimator.dao;

import com.neo4j.energyestimator.model.Message;

import java.time.LocalDateTime;
import java.util.Map;

public interface MessageStore {
    /**
     * Stores message in the message store
     * @param message
     */
    void storeMessage(String message);

    /**
     * Get all messages stored in the message store
     * @return collection of messages
     */
    Map<LocalDateTime, Message> getMessages();

    /**
     * Deletes all messages stored in the message store
     */
    void deleteMessages();
}
