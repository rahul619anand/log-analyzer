package com.neo4j.energyestimator.dao;

import com.neo4j.loganalyzer.model.EventLog;
import com.neo4j.loganalyzer.model.MessageType;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HSQLDBEventsStoreTest {

    private EventsStore eventsStore = new HSQLDBEventsStore();

    @AfterEach
    public void tearDown() {
        eventsStore.deleteMessages();
    }

    @Test
    public void storeMessagePersistsTurnOffMessage() {
        eventsStore.storeMessage("1544213763 TurnOff");
        Map<LocalDateTime, EventLog> messages = eventsStore.getMessages();
        assertEquals(1, messages.size());
        messages.entrySet().forEach(entrySet -> {
            EventLog msg = entrySet.getValue();
            assertEquals(MessageType.TurnOff, msg.getType());
            assertEquals(LocalDateTime.of(2018, 12, 7, 20, 16, 3), msg.getTime());
        });
    }

    @Test
    public void storeMessagePersistsDeltaMessage() {
        eventsStore.storeMessage("1544211963 Delta +0.75");
        Map<LocalDateTime, EventLog> messages = eventsStore.getMessages();
        assertEquals(1, messages.size());
        messages.entrySet().forEach(entrySet -> {
            EventLog msg = entrySet.getValue();
            assertEquals(MessageType.Delta, msg.getType());
            assertEquals(Optional.of(0.75), msg.getValue());
            assertEquals(LocalDateTime.of(2018, 12, 7, 19, 46, 3), msg.getTime());
        });
    }

    @Test
    public void storeMessageHandlesDeduplication() {
        eventsStore.storeMessage("1544211963 Delta +0.75");
        eventsStore.storeMessage("1544211963 Delta +0.75");
        assertEquals(1, eventsStore.getMessages().size());
    }
}