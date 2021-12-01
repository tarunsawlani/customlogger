package com.customlogger.app.test;

import com.customlogger.app.MainApplication;
import com.customlogger.configuration.BeanConfiguration;
import com.customlogger.dao.EventDetailsRepository;
import com.customlogger.service.CustomLoggerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;


@SpringBootTest
@ContextConfiguration
        (classes = {BeanConfiguration.class})

public class ApplicationTest {

    @MockBean
    private EventDetailsRepository eventDetailsRepository;

    @Autowired
    private CustomLoggerService customLoggerService;

    @Test
    public void processLogsException() throws JsonProcessingException {
        Assertions.assertThrows(JsonProcessingException.class, () -> {
            customLoggerService.processLogs("");
        });
    }

   @Test
    public void processLogs() {
        MainApplication.main(new String[]{"../newfile.txt"});

    }

}
