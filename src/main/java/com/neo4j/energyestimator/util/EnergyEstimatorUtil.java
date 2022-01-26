package com.neo4j.energyestimator.util;

import com.neo4j.energyestimator.model.Message;
import com.neo4j.energyestimator.model.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

public class EnergyEstimatorUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(EnergyEstimatorUtil.class);

    private static final double minDimmerValue = 0;
    private static final double maxDimmerValue = 1;
    private static final int energyConsumptionRate = 5;
    private static final int minutesInAnHour = 60;

    public static double getEnergyUsed(Map<LocalDateTime, Message> messageStore) {
        TreeSet<LocalDateTime> messageTimestamps = new TreeSet<>(messageStore.keySet());
        Iterator<LocalDateTime> timestampsIterator = messageTimestamps.iterator();
        LocalDateTime current = timestampsIterator.next();

        double currentDimmerValue = 0;
        double energyUsed = 0;
        while(timestampsIterator.hasNext()) {
            LocalDateTime next = timestampsIterator.next();
            Message message = messageStore.get(next);
            long dimmerChangeIntervalInMinutes = current.until(next, ChronoUnit.MINUTES);

            energyUsed += (dimmerChangeIntervalInMinutes * energyConsumptionRate * currentDimmerValue) / minutesInAnHour;

            if(message.getType().equals(MessageType.Delta)) {
                Double dimmerChangeValue = message.getValue().get();
                currentDimmerValue += dimmerChangeValue;

                if(currentDimmerValue < minDimmerValue || currentDimmerValue > maxDimmerValue) {
                    LOGGER.warn("The current dimmer value is: "+ currentDimmerValue +".  Invalid dimmer change value of: " + dimmerChangeValue);
                    currentDimmerValue -= dimmerChangeValue; // reverting current dimmer value and ignoring the message;
                }
            } else {
                currentDimmerValue = 0; // TurnOff event message
            }
            current = next;
        }
        return energyUsed;
    }
}
