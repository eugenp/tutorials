package com.example.demo.reactive;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
public class Event {
    private long id;
    private Date date;


    public Event(long id, Date date) {
        this.id = id;
        this.date = date;

    }
}
