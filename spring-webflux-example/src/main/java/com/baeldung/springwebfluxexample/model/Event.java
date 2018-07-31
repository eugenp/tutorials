package com.baeldung.springwebfluxexample.model;

import java.util.Date;

import lombok.Data;

@Data
public class Event {
    private long id;
    private Date date;

    // all args constructor
    public Event(long id, Date date) {
        this.id = id;
        this.date = date;
    }

}
