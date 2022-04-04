package com.neo4j.loganalyzer;

import com.neo4j.loganalyzer.model.EventLog;
import com.neo4j.loganalyzer.service.LogAnalyzer;
import com.neo4j.loganalyzer.util.JsonToObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class App {
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        if (args == null || args.length == 1) {
            String errMsg = "Please supply the log file correctly, e.g. --args=<filename>";
            LOGGER.error(errMsg);
            throw new IllegalArgumentException(errMsg);
        }
        String file = args[0];
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            LogAnalyzer logAnalyzer = new LogAnalyzer();
            String line;
            while ((line = reader.readLine()) != null) {
                EventLog log = JsonToObjectMapper.map(line);
                logAnalyzer.analyze(log);
            }
        } catch (IOException e) {
            LOGGER.error("Error reading log file: " + e);
        }
    }
}