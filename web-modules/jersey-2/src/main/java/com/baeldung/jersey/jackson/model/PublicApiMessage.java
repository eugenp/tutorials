package com.baeldung.jersey.jackson.model;

import java.time.LocalDate;

import com.baeldung.jersey.jackson.annotation.PublicApi;

@PublicApi
public class PublicApiMessage {
    public String text;
    public LocalDate date;
    public String sensitiveField;


    public PublicApiMessage(String text, LocalDate date, String sensitiveField) {
        this.text = text;
        this.date = date;
        this.sensitiveField = sensitiveField;
    }
}