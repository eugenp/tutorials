package com.baeldung.hexagonalarchitecture.adapters;

import com.baeldung.hexagonalarchitecture.domain.Note;
import com.baeldung.hexagonalarchitecture.ports.INoteRepo;
import com.baeldung.hexagonalarchitecture.ports.INoteWriter;

public class NoteListWriterAdapter implements INoteWriter {

        @Override public void write(String text, INoteRepo repo) {
                Note note = new Note();
                note.setText(text);
                repo.save(note);
        }
}
