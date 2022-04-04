package com.creditsuisse.loganalyzer.model;

import java.util.Objects;
import java.util.Optional;

/**
 * Event log represents a single json line in log file
 */
public class EventLog {
    private String id;
    private String state;
    private long timestamp;
    private Optional<String> host;
    private Optional<String> type;

    public EventLog () {}

    public EventLog(String id, String state, long timestamp, Optional<String> host, Optional<String> type) {
        this.id = id;
        this.state = state;
        this.timestamp = timestamp;
        this.host = host;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Optional<String> getHost() {
        return host;
    }

    public void setHost(Optional<String> host) {
        this.host = host;
    }

    public Optional<String> getType() {
        return type;
    }

    public void setType(Optional<String> type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "EventLog{" +
                "id='" + id + '\'' +
                ", state='" + state + '\'' +
                ", timestamp=" + timestamp +
                ", host=" + host +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventLog eventLog = (EventLog) o;
        return timestamp == eventLog.timestamp &&
                Objects.equals(id, eventLog.id) &&
                Objects.equals(state, eventLog.state) &&
                Objects.equals(host, eventLog.host) &&
                Objects.equals(type, eventLog.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, state, timestamp, host, type);
    }
}
