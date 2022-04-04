package com.creditsuisse.loganalyzer.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility to get a singleton connection to HSQL DB
 */
public class ConnectionUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionUtil.class);
    private static String connectionURL = "jdbc:hsqldb:file:hsqldb/eventsStore";

    private static volatile Connection connection;
    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            synchronized (ConnectionUtil.class) {
                if (connection == null) {
                    connection = DriverManager.getConnection(connectionURL, "SA", "");
                    LOGGER.info("Connected to DB URL:"+ connectionURL);
                }
            }
        }
        return connection;
    }

    public static void closeConnection() throws SQLException {
        connection.close();
        LOGGER.info("Closed DB connection");
    }
}
