package com.customlogger.configuration;

import com.customlogger.dao.EventDetailsRepository;
import com.customlogger.service.CustomLoggerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public CustomLoggerService customLoggerService() {
        return new CustomLoggerService();
    }


    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
