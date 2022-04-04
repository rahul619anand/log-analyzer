package com.neo4j.loganalyzer.service;

import com.neo4j.loganalyzer.model.Event;
import com.neo4j.loganalyzer.model.EventLog;

import java.util.HashMap;
import java.util.Map;

public class LogAnalyzer {
    Map<String, EventLog> eventMap = new HashMap<>();

    public LogAnalyzer () {

    }

    public void analyze(EventLog currentLog) {
        String id = currentLog.getId();
        if (eventMap.containsKey(id)) {
            EventLog previousLog = eventMap.remove(id);
            long duration = Math.abs(currentLog.getTimestamp() - previousLog.getTimestamp());
            boolean alert = false;
            if (duration > 4) {
                alert = true;
            }

            Event event = new Event(id, duration, alert, currentLog.getHost(), currentLog.getType());
//        dao.writeEvent(event);
        } else {
            eventMap.put(id, currentLog);
        }
    }
}
