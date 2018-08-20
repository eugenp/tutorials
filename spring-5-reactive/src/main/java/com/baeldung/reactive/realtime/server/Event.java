package com.baeldung.reactive.realtime.server;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Event {
    private String id;
    private Date date;
}
