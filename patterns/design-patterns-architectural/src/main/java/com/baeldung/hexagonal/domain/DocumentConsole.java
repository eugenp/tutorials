package com.baeldung.hexagonal.domain;

import java.time.LocalDateTime;

public class DocumentConsole {

    private String id;
    private String name;
    private LocalDateTime creationDate;
    private String note;

    public DocumentConsole(String id, String name, LocalDateTime creationDate, String note) {
        super();
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
        this.note = note;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
