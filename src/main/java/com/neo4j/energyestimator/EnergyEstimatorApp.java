package com.neo4j.energyestimator;

import com.neo4j.energyestimator.model.Message;
import com.neo4j.energyestimator.util.EnergyEstimatorUtil;
import com.neo4j.energyestimator.dao.InMemoryMessageStore;
import com.neo4j.energyestimator.dao.MessageStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.Map;

public class EnergyEstimatorApp {
    private static final Logger LOGGER = LoggerFactory.getLogger(EnergyEstimatorUtil.class);

    public static void main(String[] args) {
        MessageStore messageStore = new InMemoryMessageStore();
        try {
            InputStreamReader in= new InputStreamReader(System.in);
            BufferedReader reader = new BufferedReader(in);
            String message;
            while ((message = reader.readLine()) != null) {
                messageStore.storeMessage(message);
            }
            Map<LocalDateTime, Message> messages = messageStore.getMessages();
            LOGGER.debug("Stored Messages: " + messages);
            double energyUsed = EnergyEstimatorUtil.getEnergyUsed(messages);
            LOGGER.info("Estimated energy used: " + energyUsed + "Wh");
        } catch (IOException e) {
            LOGGER.error("Issue reading stdin: " + e);
        }
    }
}