package com.baeldung.hexagonalarchitecture.ports;

import com.baeldung.hexagonalarchitecture.domain.Note;

import java.util.List;

public interface INoteRepo {

    void save(Note note);

    List<Note> findAll();
}
