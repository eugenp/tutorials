package com.baeldung.hexagonalarchitecture.ports;

import com.baeldung.hexagonalarchitecture.domain.Note;

import java.util.List;

public interface INoteReader {

        List<Note> read(INoteRepo repo);
}
