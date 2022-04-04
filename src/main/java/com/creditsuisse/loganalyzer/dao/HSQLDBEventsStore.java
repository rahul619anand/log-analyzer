package com.creditsuisse.loganalyzer.dao;

import com.creditsuisse.loganalyzer.model.Event;
import com.creditsuisse.loganalyzer.util.EventLogObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HSQLDBEventsStore implements EventsStore {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventLogObjectMapper.class);
    private static final String table = "events";
    private static final String insertEventQuery = "INSERT INTO " + table + " (id, duration, alert, type, host)  VALUES (?, ?, ?, ?, ?)";
    private static final String createEventsTableQuery = "CREATE TABLE IF NOT EXISTS " + table +
            "(id VARCHAR(100) NOT NULL," +
            "duration FLOAT NOT NULL, " +
            "alert BOOLEAN NOT NULL," +
            "type VARCHAR(100)," +
            "host VARCHAR(100))";
    private static String deleteEventsQuery = "DELETE FROM " + table;

    private Connection connection;
    public HSQLDBEventsStore(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createEventsTable() throws SQLException {
        connection.createStatement().executeUpdate(createEventsTableQuery);
        LOGGER.info("Created 'events' table");
    }

    @Override
    public void writeEvent(Event event) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(insertEventQuery);
        preparedStatement.setString(1, event.getId());
        preparedStatement.setLong(2, event.getDuration());
        preparedStatement.setBoolean(3, event.isAlert());
        preparedStatement.setString(4, event.getType().orElse(null));
        preparedStatement.setString(5, event.getHost().orElse(null));
        preparedStatement.executeUpdate();
        LOGGER.info("Event written to DB: "+ event);
    }

    @Override
    public void deleteEvents() throws SQLException {
        connection.createStatement().executeUpdate(deleteEventsQuery);
        LOGGER.info("Deleted all events from DB");
    }

    @Override
    public List<Event> getEvents() throws SQLException {
        List<Event> events = new ArrayList<>();
        String getAll = "SELECT * FROM " + table;

        ResultSet resultSet = connection.createStatement().executeQuery(getAll);

        while (resultSet.next()) {
            Event event = new Event(
                    resultSet.getString(1),
                    resultSet.getLong(2),
                    resultSet.getBoolean(3),
                    Optional.ofNullable(resultSet.getString(4)),
                    Optional.ofNullable(resultSet.getString(5))
            );
            events.add(event);
        }
        LOGGER.info("All events in DB: " + events);
        return events;
    }
}
