package com.baeldung.hexagonalarchitecture.adapters;

import com.baeldung.hexagonalarchitecture.domain.Note;
import com.baeldung.hexagonalarchitecture.ports.INoteRepo;

import java.util.ArrayList;
import java.util.List;

public class NoteListRepoAdapter implements INoteRepo {

        private List<Note> notes;

        @Override
        public void save(Note note) {
                if (notes == null) {
                        notes = new ArrayList<>();
                }
                notes.add(note);
        }

        @Override
        public List<Note> findAll() {
                return notes;
        }
}
