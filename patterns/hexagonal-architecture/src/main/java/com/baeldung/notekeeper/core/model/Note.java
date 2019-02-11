package com.baeldung.notekeeper.core.model;

public class Note {

    private Long id;
    private String body;

    public Note(String body) {
        this.body = body;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", body='" + body + '\'' +
                '}';
    }

}
