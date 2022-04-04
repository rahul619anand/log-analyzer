package com.creditsuisse.loganalyzer.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.creditsuisse.loganalyzer.model.EventLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonToObjectMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonToObjectMapper.class);
    private ObjectMapper mapper;

    public JsonToObjectMapper () {
        mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());
    }

    public EventLog map(String json) throws JsonProcessingException {
        LOGGER.debug("JSON line:", json);
        EventLog eventLog = mapper.readValue(json, EventLog.class);
        LOGGER.debug("Decoded event Log:", eventLog);
        return eventLog;
    }
}
