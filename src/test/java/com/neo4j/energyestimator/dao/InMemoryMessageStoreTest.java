package com.neo4j.energyestimator.dao;

import com.neo4j.energyestimator.model.Message;
import com.neo4j.energyestimator.model.MessageType;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InMemoryMessageStoreTest {

    private MessageStore messageStore = new InMemoryMessageStore();

    @AfterEach
    public void tearDown() {
        messageStore.deleteMessages();
    }

    @Test
    public void storeMessagePersistsTurnOffMessage() {
        messageStore.storeMessage("1544213763 TurnOff");
        Map<LocalDateTime, Message> messages = messageStore.getMessages();
        assertEquals(1, messages.size());
        messages.entrySet().forEach(entrySet -> {
            Message msg = entrySet.getValue();
            assertEquals(MessageType.TurnOff, msg.getType());
            assertEquals(LocalDateTime.of(2018, 12, 7, 20, 16, 3), msg.getTime());
        });
    }

    @Test
    public void storeMessagePersistsDeltaMessage() {
        messageStore.storeMessage("1544211963 Delta +0.75");
        Map<LocalDateTime, Message> messages = messageStore.getMessages();
        assertEquals(1, messages.size());
        messages.entrySet().forEach(entrySet -> {
            Message msg = entrySet.getValue();
            assertEquals(MessageType.Delta, msg.getType());
            assertEquals(Optional.of(0.75), msg.getValue());
            assertEquals(LocalDateTime.of(2018, 12, 7, 19, 46, 3), msg.getTime());
        });
    }

    @Test
    public void storeMessageHandlesDeduplication() {
        messageStore.storeMessage("1544211963 Delta +0.75");
        messageStore.storeMessage("1544211963 Delta +0.75");
        assertEquals(1, messageStore.getMessages().size());
    }
}