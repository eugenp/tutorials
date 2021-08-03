package com.baeldung.note.businesslogic;

public class Note {
    private final Long id;
    private final String description;
    private final boolean done;

    public Note(Long id, String description, boolean done) {
        this.id = id;
        this.description = description;
        this.done = done;
    }

    public String format() {
        return id + ";" + description + ";" + done + ';';
    }
}
