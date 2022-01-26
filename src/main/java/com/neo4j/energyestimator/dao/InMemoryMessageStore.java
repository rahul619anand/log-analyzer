package com.neo4j.energyestimator.dao;

import com.neo4j.energyestimator.model.Message;
import com.neo4j.energyestimator.model.MessageType;
import com.neo4j.energyestimator.util.EnergyEstimatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryMessageStore implements MessageStore {
    private static final Logger LOGGER = LoggerFactory.getLogger(EnergyEstimatorUtil.class);

    private Map<LocalDateTime, Message> messages = new HashMap<>();
    private static final String delimiter = " ";
    private static final double minDimmerChangeValue = -1.0;
    private static final double maxDimmerChangeValue = 1.0;

    public void storeMessage(String message) {
            String[] arr = message.split(delimiter);
            LocalDateTime time = LocalDateTime.ofInstant(Instant.ofEpochSecond(Long.valueOf(arr[0])), ZoneId.systemDefault());
            Message msg = new Message();
            msg.setTime(time);
            MessageType type = MessageType.valueOf(arr[1]);
            msg.setType(type);

            if (type.equals(MessageType.Delta)) {
                double value = Double.valueOf(arr[2]);
                if (value >= minDimmerChangeValue && value <= maxDimmerChangeValue) {
                    msg.setValue(Optional.of(value));
                    messages.put(time, msg);
                } else {
                    LOGGER.warn("Invalid dimmer change value, ignoring the message");
                }
            }

            if (type.equals(MessageType.TurnOff)) {
                messages.put(time, msg);
            }
    }

    public Map<LocalDateTime, Message> getMessages() {
        return messages;
    }

    public void deleteMessages() {
        messages.clear();
    }
}
