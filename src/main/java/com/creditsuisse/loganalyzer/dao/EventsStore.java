package com.neo4j.energyestimator.dao;

import com.neo4j.loganalyzer.model.Event;
import com.neo4j.loganalyzer.model.EventLog;

import java.time.LocalDateTime;
import java.util.Map;

public interface EventsStore {
    /**
     * Stores events in the events table
     * @param message
     */
    void writeEvent(Event event);

}
