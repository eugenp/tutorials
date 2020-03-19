package com.baeldung.hexagonal.domain;

public class DocumentConsoleDto {

    private String id;
    private String name;
    private String note;

    public DocumentConsoleDto(String name, String note) {
        super();
        this.name = name;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
