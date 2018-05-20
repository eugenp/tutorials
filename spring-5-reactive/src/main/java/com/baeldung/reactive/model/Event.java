package com.baeldung.reactive.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Event {
    private int id;
    private String action;
}
