package com.baeldung.jersey.jackson.model;

import java.time.LocalDate;
import java.util.List;

public class Message {
    public String text;
    public LocalDate date;
    public String optionalField;
    public List<String> metadata;

    public Message(String text, LocalDate date, String optionalField) {
        this.text = text;
        this.date = date;
        this.optionalField = optionalField;
    }
}
