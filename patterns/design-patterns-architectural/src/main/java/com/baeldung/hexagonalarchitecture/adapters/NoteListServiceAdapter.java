package com.baeldung.hexagonalarchitecture.adapters;

import com.baeldung.hexagonalarchitecture.domain.Note;
import com.baeldung.hexagonalarchitecture.ports.INoteRepo;
import com.baeldung.hexagonalarchitecture.ports.INoteService;

import java.util.List;

public class NoteListServiceAdapter implements INoteService {

        private INoteRepo repo;

        public NoteListServiceAdapter(INoteRepo repo) {
                this.repo = repo;
        }

        @Override
        public List<Note> read() {
                return this.repo.findAll();
        }

        @Override
        public void write(String text) {
                Note note = new Note();
                note.setText(text);
                repo.save(note);
        }
}
