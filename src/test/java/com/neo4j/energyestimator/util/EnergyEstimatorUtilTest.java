package com.neo4j.energyestimator.util;

import com.neo4j.energyestimator.model.Message;
import com.neo4j.energyestimator.model.MessageType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class EnergyEstimatorUtilTest {

    @Test
    public void getEnergyUsedScenario1() {
        Map<LocalDateTime, Message> messages = new HashMap<>();

        Message msg = new Message();
        msg.setType(MessageType.TurnOff);
        LocalDateTime localDateTime = LocalDateTime.of(2018, 12, 7, 18, 16, 2);
        msg.setTime(localDateTime);
        messages.put(localDateTime, msg);

        msg = new Message();
        msg.setType(MessageType.Delta);
        localDateTime = LocalDateTime.of(2018, 12, 7, 18, 16, 3);
        msg.setTime(localDateTime);
        msg.setValue(Optional.of(0.5));
        messages.put(localDateTime, msg);

        msg = new Message();
        msg.setType(MessageType.TurnOff);
        localDateTime = LocalDateTime.of(2018, 12, 7, 19, 16, 3);
        msg.setTime(localDateTime);
        messages.put(localDateTime, msg);

        assertEquals(2.5, EnergyEstimatorUtil.getEnergyUsed(messages), 0.0);
    }

    @Test
    public void getEnergyUsedScenario2() {
        Map<LocalDateTime, Message> messages = new HashMap<>();

        Message msg = new Message();
        msg.setType(MessageType.TurnOff);
        LocalDateTime localDateTime = LocalDateTime.of(2018, 12, 7, 18, 16, 2);
        msg.setTime(localDateTime);
        messages.put(localDateTime, msg);

        msg = new Message();
        msg.setType(MessageType.Delta);
        localDateTime = LocalDateTime.of(2018, 12, 7, 18, 16, 3);
        msg.setTime(localDateTime);
        msg.setValue(Optional.of(0.5));
        messages.put(localDateTime, msg);

        msg = new Message();
        msg.setType(MessageType.Delta);
        localDateTime = LocalDateTime.of(2018, 12, 7, 19, 16, 3);
        msg.setTime(localDateTime);
        msg.setValue(Optional.of(-0.25));
        messages.put(localDateTime, msg);

        msg = new Message();
        msg.setType(MessageType.Delta);
        localDateTime = LocalDateTime.of(2018, 12, 7, 19, 46, 3);
        msg.setTime(localDateTime);
        msg.setValue(Optional.of(0.75));
        messages.put(localDateTime, msg);

        msg = new Message();
        msg.setType(MessageType.Delta);
        localDateTime = LocalDateTime.of(2018, 12, 7, 19, 46, 3);
        msg.setTime(localDateTime);
        msg.setValue(Optional.of(0.75));
        messages.put(localDateTime, msg);

        msg = new Message();
        msg.setType(MessageType.TurnOff);
        localDateTime = LocalDateTime.of(2018, 12, 7, 20, 16, 3);
        msg.setTime(localDateTime);
        messages.put(localDateTime, msg);

        assertEquals(5.625, EnergyEstimatorUtil.getEnergyUsed(messages), 0.0);
    }
}