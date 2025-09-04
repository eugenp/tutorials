package com.baeldung.jersey.jackson.model;

import java.time.LocalDate;
import java.util.List;

import com.baeldung.jersey.jackson.annotation.InternalApi;

@InternalApi
public class InternalApiMessage {
    public String text;
    public LocalDate date;
    public String debugInfo;
    public List<String> metadata;


    public InternalApiMessage(String text, LocalDate date, String debugInfo, List<String> metadata) {
        this.text = text;
        this.date = date;
        this.debugInfo = debugInfo;
        this.metadata = metadata;
    }
}