package com.baeldung.ksqldb;

import lombok.Data;

@Data
public class Reading {
    private String id;
    private String timestamp;
    private int reading;
}
