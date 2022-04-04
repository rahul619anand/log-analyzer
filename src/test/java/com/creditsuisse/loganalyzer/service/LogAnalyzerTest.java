package com.creditsuisse.loganalyzer.service;

import com.creditsuisse.loganalyzer.dao.HSQLDBEventsStore;
import com.creditsuisse.loganalyzer.model.Event;
import com.creditsuisse.loganalyzer.model.EventLog;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class LogAnalyzerTest {
    @InjectMocks private LogAnalyzer mockLogAnalyzer;
    @Mock private HSQLDBEventsStore mockEventsStore;

    @BeforeAll
    public static void setup() {
        MockitoAnnotations.openMocks(LogAnalyzerTest.class);
    }

    @Test
    public void analyzeAndStoreEvent() throws SQLException {
        Event event = new Event("scsmbstgra", 5, true, Optional.empty(), Optional.empty());
        Mockito.lenient().doNothing().when(mockEventsStore).writeEvent(event);
        EventLog startEventLog = new EventLog("scsmbstgra", "STARTED", 1491377495212L, null, null);
        EventLog finishedEventLog = new EventLog("scsmbstgra", "STARTED", 1491377495217L, null, null);

        mockLogAnalyzer.analyzeAndStoreEvent(startEventLog);
        mockLogAnalyzer.analyzeAndStoreEvent(finishedEventLog);

        Mockito.verify(mockEventsStore).writeEvent(event);
    }

}