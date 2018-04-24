package com.baeldung.reactive.webflux;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Event {

    private Long id;
    private String body;

}
