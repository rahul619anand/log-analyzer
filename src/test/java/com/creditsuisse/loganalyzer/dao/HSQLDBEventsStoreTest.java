package com.creditsuisse.loganalyzer.dao;

import java.sql.*;

import com.creditsuisse.loganalyzer.model.Event;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class HSQLDBEventsStoreTest {
    @InjectMocks private HSQLDBEventsStore mockEventsStore;
    @Mock private Connection mockConnection;
    @Mock private Statement mockStatement;
    @Mock private PreparedStatement mockPreparedStatement;
    @Mock private ResultSet mockResultSet;

    @BeforeAll
    public static void setup() {
        MockitoAnnotations.openMocks(HSQLDBEventsStoreTest.class);
    }

    @Test
    public void createEventsTable() throws SQLException {
        Mockito.when(mockConnection.createStatement()).thenReturn(mockStatement);
        Mockito.when(mockStatement.executeUpdate(Mockito.anyString())).thenReturn(1);

        mockEventsStore.createEventsTable();

        Mockito.verify(mockConnection).createStatement();
        Mockito.verify(mockStatement).executeUpdate(Mockito.anyString());
    }

    @Test
    public void writeEvent() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString())).thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        Event event = new Event("id", 5, true, null, null);
        mockEventsStore.writeEvent(event);

        Mockito.verify(mockConnection).prepareStatement(Mockito.anyString());
    }

    @Test
    public void getEvents() throws SQLException {
        Mockito.when(mockConnection.createStatement()).thenReturn(mockStatement);
        Mockito.when(mockStatement.executeQuery(Mockito.anyString())).thenReturn(mockResultSet);

        mockEventsStore.getEvents();

        Mockito.verify(mockConnection).createStatement();
        Mockito.verify(mockStatement).executeQuery(Mockito.anyString());
    }

    @Test
    public void deleteEvents() throws SQLException {
        Mockito.when(mockConnection.createStatement()).thenReturn(mockStatement);
        Mockito.when(mockStatement.executeUpdate(Mockito.anyString())).thenReturn(1);

        mockEventsStore.deleteEvents();

        Mockito.verify(mockConnection).createStatement();
        Mockito.verify(mockStatement).executeUpdate(Mockito.anyString());
    }
}