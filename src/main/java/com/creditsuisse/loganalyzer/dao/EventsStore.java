package com.creditsuisse.loganalyzer.dao;

import com.creditsuisse.loganalyzer.model.Event;

import java.sql.SQLException;
import java.util.List;

/**
 * DAO with behavior to interact with the EventsStore DB
 */
public interface EventsStore {
    /**
     * Creates Events Table
     * @throws SQLException
     */
    void createEventsTable() throws SQLException;

    /**
     * Stores event to the events store
     * @param event
     * @throws SQLException
     */
    void writeEvent(Event event) throws SQLException;


    /**
     * Delete all events from the events store
     * @throws SQLException
     */
    void deleteEvents() throws SQLException;


    /**
     * Get all events in events store
     * @return
     * @throws SQLException
     */
    List<Event> getEvents() throws SQLException;

}
