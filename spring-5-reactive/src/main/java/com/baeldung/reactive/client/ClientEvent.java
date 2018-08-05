package com.baeldung.reactive.client;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ClientEvent {
    private long id;
    private Date date;
}
