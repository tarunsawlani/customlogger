package com.customlogger.model;

import com.fasterxml.jackson.annotation.JsonTypeId;
import lombok.Data;

@Data
public class CustomLogger {

    String id;
    String state;
    String type;
    String host;
    long timestamp;
}
