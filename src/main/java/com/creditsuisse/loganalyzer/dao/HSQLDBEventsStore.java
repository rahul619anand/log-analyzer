package com.neo4j.energyestimator.dao;

import com.neo4j.loganalyzer.model.Event;
import com.neo4j.loganalyzer.model.EventLog;
import com.neo4j.loganalyzer.model.MessageType;
import com.neo4j.loganalyzer.util.JsonToObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HSQLDBEventsStore implements EventsStore {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonToObjectMapper.class);


    @Override
    public void writeEvent(Event event) throws SQLException {


    }
}
