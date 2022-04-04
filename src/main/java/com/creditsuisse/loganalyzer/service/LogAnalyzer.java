package com.creditsuisse.loganalyzer.service;

import com.creditsuisse.loganalyzer.dao.EventsStore;
import com.creditsuisse.loganalyzer.model.Event;
import com.creditsuisse.loganalyzer.model.EventLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Log Analyzer maintains an in-memory map to store the event logs unless a corresponding START or FINISHED event is received for an event id.
 * Once both the START and FINISHED event logs are received for an id, it calculates the duration and sets the alert flag.
 * At this moment the event records are removed from the map.
 * The event data along with the duration and alert flag is then written to the DB.
 */
public class LogAnalyzer {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogAnalyzer.class);
    private static final int alertThreshold = 4;
    private Map<String, EventLog> eventMap = new HashMap<>();
    private EventsStore eventsStore ;
    public LogAnalyzer (EventsStore eventsStore) {
        this.eventsStore = eventsStore;
    }

    public void analyzeAndStoreEvent(EventLog currentLog) {
        String id = currentLog.getId();
        if (eventMap.containsKey(id)) {
            LOGGER.debug("Current log for event id "+ id + ":" + currentLog);

            // remove is invoked to prevent the map from growing in case of processing very large files
            EventLog previousLog = eventMap.remove(id);
            LOGGER.debug("Previous log for event id "+ id + ":" + previousLog);

            // calculate duration in ms
            long duration = Math.abs(currentLog.getTimestamp() - previousLog.getTimestamp());

            boolean alert = false;
            if (duration > alertThreshold) {
                alert = true;
            }

            Event event = new Event(id, duration, alert, currentLog.getHost(), currentLog.getType());
            LOGGER.debug("Generated event to store in DB: "+ event);
            try {
                eventsStore.writeEvent(event);
            } catch (SQLException sqlException) {
                LOGGER.error("Error executing SQL: "+ sqlException);
            }
        } else {
            eventMap.put(id, currentLog);
        }
    }
}
