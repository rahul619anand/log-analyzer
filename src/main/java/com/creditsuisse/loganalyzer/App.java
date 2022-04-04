package com.creditsuisse.loganalyzer;

import com.creditsuisse.loganalyzer.dao.EventsStore;
import com.creditsuisse.loganalyzer.dao.HSQLDBEventsStore;
import com.creditsuisse.loganalyzer.util.ConnectionUtil;
import com.creditsuisse.loganalyzer.util.EventLogObjectMapper;
import com.creditsuisse.loganalyzer.model.EventLog;
import com.creditsuisse.loganalyzer.service.LogAnalyzer;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * App class orchestrating the processing and does the following:
 * - Takes the <logfile> path as input
 * - Reads each line of the file
 * - Calls log analyzer to analyze and store each event in DB
 * - Gets all events from DB and logs them
 * - Deletes all events from DB
 * - Closes DB connections, buffered reader and terminates
 */
public class App {
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        if (args == null || args.length != 1) {
            String errMsg = "Please supply the log file path correctly, e.g. --args=<file path>";
            LOGGER.error(errMsg);
            throw new IllegalArgumentException(errMsg);
        }

        // read file path
        String filePath = args[0];
        BufferedReader reader = null;
        try {

            // get DB connection
            Connection connection = ConnectionUtil.getConnection();
            EventsStore eventsStore = new HSQLDBEventsStore(connection);

            EventLogObjectMapper eventLogObjectMapper = new EventLogObjectMapper();

            // create events table
            eventsStore.createEventsTable();

            LogAnalyzer logAnalyzer = new LogAnalyzer(eventsStore);
            reader = new BufferedReader(new FileReader(filePath));
            LOGGER.info("Started reading log file: "+ filePath);
            String line;

            // reading serially a single line at a time helps to process very large files without needing much memory
            while ((line = reader.readLine()) != null) {
                LOGGER.debug("Read event log line: "+ line);
                EventLog log = null;
                try {
                    // convert json to object
                    log = eventLogObjectMapper.toEventLog(line);
                } catch (JsonProcessingException jsonProcessingException) {
                    LOGGER.error("Error parsing JSON: "+ jsonProcessingException);
                }

                // process event log and store in DB
                logAnalyzer.analyzeAndStoreEvent(log);
            }

            LOGGER.info("Finished reading log file: "+ filePath);
            // gets all events from DB and logs it
            eventsStore.getEvents();

            // delete all events from DB
            eventsStore.deleteEvents();

        } catch (IOException ioException) {
            LOGGER.error("Error reading log file: "+ ioException);
        } catch (SQLException sqlException) {
            LOGGER.error("Error executing SQL: "+ sqlException);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                ConnectionUtil.closeConnection();
            } catch (IOException ioException) {
                LOGGER.error("Error closing buffered reader: "+ ioException);
            } catch (SQLException sqlException) {
                LOGGER.error("Error closing DB connection: "+ sqlException);
            }
        }
    }
}