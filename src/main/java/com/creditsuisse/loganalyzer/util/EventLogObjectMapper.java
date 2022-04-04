package com.creditsuisse.loganalyzer.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.creditsuisse.loganalyzer.model.EventLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility to convert a line of event log in json format to corresponding Object.
 */
public class EventLogObjectMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventLogObjectMapper.class);
    private ObjectMapper mapper;

    public EventLogObjectMapper() {
        mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());
    }

    public EventLog toEventLog(String json) throws JsonProcessingException {
        LOGGER.debug("JSON line:"+ json);
        EventLog eventLog = mapper.readValue(json, EventLog.class);
        LOGGER.debug("Decoded event Log:"+ eventLog);
        return eventLog;
    }
}
