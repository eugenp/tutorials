package com.baeldung.reactive.eventstreaming;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsEvent {

    private int id;
    private LocalDateTime timeStamp;
    private String newsText;

    // standard getters and setters
}
