package com.baeldung.hexagonalarchitecture.ports;

import com.baeldung.hexagonalarchitecture.domain.Note;

import java.util.List;

public interface INoteService {

        List<Note> read();

        void write(String text);
}
