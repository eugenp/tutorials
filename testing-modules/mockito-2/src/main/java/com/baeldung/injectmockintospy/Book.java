package com.baeldung.injectmockintospy;

import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Book {
    private String name;
    private String author;
    private long timesTaken;
    private ZonedDateTime returnDate;

    public Book(String name, String author, long timesTaken) {
        this.name = name;
        this.author = author;
        this.timesTaken = timesTaken;
    }
}
