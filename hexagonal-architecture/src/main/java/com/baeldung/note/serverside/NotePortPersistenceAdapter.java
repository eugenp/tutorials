package com.baeldung.note.serverside;

import com.baeldung.note.businesslogic.LoadNotePort;
import com.baeldung.note.businesslogic.Note;

import java.util.List;

public class NotePortPersistenceAdapter implements LoadNotePort {

    public List<Note> findAll() {
        return List.of(
                new Note(1L, "Buy shoes", true),
                new Note(2L, "Write baledung post", false),
                new Note(3L, "Bring books to library", false),
                new Note(4L, "Clean desk", false)
        );
    }
}
