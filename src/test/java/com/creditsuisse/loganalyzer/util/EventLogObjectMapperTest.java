package com.creditsuisse.loganalyzer.util;

import com.creditsuisse.loganalyzer.model.EventLog;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;


public class EventLogObjectMapperTest {

    private EventLogObjectMapper eventLogObjectMapper = new EventLogObjectMapper();

    @Test
    public void testMap() throws JsonProcessingException {
        String json = "{\"id\":\"scsmbstgrb\", \"state\":\"STARTED\", \"timestamp\":1491377495213}";
        EventLog eventLog = new EventLog("scsmbstgrb", "STARTED", 1491377495213L, null, null);
        assertEquals(eventLog, eventLogObjectMapper.toEventLog(json));
    }

    @Test
    public void testMapWithHostAndType() throws JsonProcessingException {
        String json = "{\"id\":\"scsmbstgra\", \"state\":\"STARTED\", \"type\":\"APPLICATION_LOG\", \"host\":\"12345\", \"timestamp\":1491377495212}";
        EventLog eventLog = new EventLog("scsmbstgra", "STARTED", 1491377495212L, Optional.of("12345"), Optional.of("APPLICATION_LOG"));
        assertEquals(eventLog, eventLogObjectMapper.toEventLog(json));
    }
}