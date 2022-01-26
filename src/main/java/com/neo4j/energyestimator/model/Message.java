package com.neo4j.energyestimator.model;

import java.time.LocalDateTime;
import java.util.Optional;

public class Message {
    private MessageType type;
    private LocalDateTime time;
    private Optional<Double> value = Optional.empty();

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Optional<Double> getValue() {
        return value;
    }

    public void setValue(Optional<Double> value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "com.neo4j.energyestimator.model.Message{" +
                "type=" + type +
                ", time=" + time +
                ", value=" + value +
                '}';
    }
}
