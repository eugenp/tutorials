package com.baeldung.hexagonalarchitecture.adapters;

import com.baeldung.hexagonalarchitecture.domain.Note;
import com.baeldung.hexagonalarchitecture.ports.INoteReader;
import com.baeldung.hexagonalarchitecture.ports.INoteRepo;

import java.util.List;

public class NoteListReaderAdapter implements INoteReader {

        @Override public List<Note> read(INoteRepo repo) {
                return repo.findAll();
        }

}
