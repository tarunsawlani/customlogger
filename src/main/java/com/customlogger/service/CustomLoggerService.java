package com.customlogger.service;

import com.customlogger.dao.EventDetails;
import com.customlogger.dao.EventDetailsRepository;
import com.customlogger.model.CustomLogger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
@Service
public class CustomLoggerService {

    private Map<String, CustomLogger> cacheEvents = new ConcurrentHashMap<>();

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private EventDetailsRepository eventDetailsRepository;

    public void processLogs(String line) throws JsonProcessingException {

        try {
            CustomLogger customerLogger = objectMapper.readValue(line, CustomLogger.class);
            if (cacheEvents.containsKey(customerLogger.getId())) {

                EventDetails eventDetails = new EventDetails();
                eventDetails.setId(customerLogger.getId());
                eventDetails.setHost(customerLogger.getHost() != null ? customerLogger.getHost() : "");
                eventDetails.setType(customerLogger.getType() != null ? customerLogger.getType() : "");
                long timeTaken = 0;
                if ("STARTED".equalsIgnoreCase(customerLogger.getState())) {
                    timeTaken = cacheEvents.get(customerLogger.getId()).getTimestamp() - customerLogger.getTimestamp();
                    eventDetails.setTimetaken(timeTaken);
                } else {
                    timeTaken = customerLogger.getTimestamp() - cacheEvents.get(customerLogger.getId()).getTimestamp();

                }

                eventDetails.setTimetaken(timeTaken);
                if (timeTaken > 4) {
                    eventDetails.setAlert(true);
                    log.info("Long time alert.Event Detail id={} took longer time {} ms", eventDetails.getId(), eventDetails.getTimetaken());
                }
                eventDetailsRepository.save(eventDetails);
                log.info("Event Detail id={} added to Database", eventDetails.getId());
                cacheEvents.remove(eventDetails.getId());
                log.debug("Removed id={} to cache", customerLogger.getId());
            } else {
                log.debug("Adding id={} to cache", customerLogger.getId());
                cacheEvents.put(customerLogger.getId(), customerLogger);
            }
        } catch (JsonProcessingException e) {
            log.error("Exception occurred while parsing "+ e.getMessage());
            throw e;
        }
    }
}
