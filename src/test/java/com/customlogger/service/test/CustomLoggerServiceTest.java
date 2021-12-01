package com.customlogger.service.test;

import com.customlogger.dao.EventDetails;
import com.customlogger.dao.EventDetailsRepository;
import com.customlogger.service.CustomLoggerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;

public class CustomLoggerServiceTest {


    @InjectMocks
    private CustomLoggerService customLoggerService;

    @Mock
    private EventDetailsRepository eventDetailsRepository;

    @Captor
    private ArgumentCaptor<EventDetails> eventDetailsArgumentCaptor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void testProcessLogs() throws JsonProcessingException {

        Mockito.when(eventDetailsRepository.save(eventDetailsArgumentCaptor.capture())).thenReturn(new EventDetails());
        customLoggerService.processLogs("{\"id\":\"scsmbstgra\", \"state\":\"FINISHED\", \"type\":\"APPLICATION_LOG\", \"host\":\"12345\", \"timestamp\":1491377495217}");
        customLoggerService.processLogs("{\"id\":\"scsmbstgra\", \"state\":\"STARTED\", \"type\":\"APPLICATION_LOG\", \"host\":\"12345\", \"timestamp\":1491377495211}");

        assertEquals("scsmbstgra", eventDetailsArgumentCaptor.getValue().getId());
        Mockito.verify(eventDetailsRepository, times(1)).save(Mockito.any());

    }


    @Test(expected = JsonProcessingException.class)
    public void testProcessLogsException() throws JsonProcessingException {

        Mockito.when(eventDetailsRepository.save(eventDetailsArgumentCaptor.capture())).thenReturn(new EventDetails());
        customLoggerService.processLogs("dddd{\"id\":\"scsmbstgra\", \"state\":\"FINISHED\", \"type\":\"APPLICATION_LOG\", \"host\":\"12345\", \"timestamp\":1491377495217}");
        customLoggerService.processLogs("{\"id\":\"scsmbstgra\", \"state\":\"STARTED\", \"type\":\"APPLICATION_LOG\", \"host\":\"12345\", \"timestamp\":1491377495211}");



    }

}
