package com.customlogger.dao;

import lombok.Data;


import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "eventdetail")
@Table(name = "EVENT_DETAILS")
@Data
public class EventDetails implements Serializable {
    @Id
    @Column(name = "ID")
    String id;

    @Column(name = "ALERT")
    boolean alert;

    @Column(name = "TYPE")
    String type;

    @Column(name = "HOST")
    String host;

    @Column(name = "TIMETAKEN")
    long timetaken;
}





