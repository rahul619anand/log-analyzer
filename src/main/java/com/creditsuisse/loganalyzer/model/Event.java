package com.creditsuisse.loganalyzer.model;

import java.util.Objects;
import java.util.Optional;

/**
 * Event object represents a single event row stored in HSQL DB
 */
public class Event {
    private String id;
    private long duration;
    private boolean alert;
    private Optional<String> host;
    private Optional<String> type;

    public Event(String id, long duration, boolean alert, Optional<String> host, Optional<String> type) {
        this.id = id;
        this.duration = duration;
        this.alert = alert;
        if(host == null) {
            this.host = Optional.empty();
        } else {
            this.host = host;
        }
        if(type == null) {
            this.type = Optional.empty();
        } else {
            this.type = type;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public boolean isAlert() {
        return alert;
    }

    public void setAlert(boolean alert) {
        this.alert = alert;
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
        return "Event{" +
                "id='" + id + '\'' +
                ", duration=" + duration +
                ", alert=" + alert +
                ", host=" + host +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return duration == event.duration &&
                alert == event.alert &&
                id.equals(event.id) &&
                Objects.equals(host, event.host) &&
                Objects.equals(type, event.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, duration, alert, host, type);
    }
}