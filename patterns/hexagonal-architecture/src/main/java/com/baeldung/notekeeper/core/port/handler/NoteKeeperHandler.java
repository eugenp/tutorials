package com.baeldung.notekeeper.core.port.handler;

import com.baeldung.notekeeper.core.model.Note;

import java.util.Optional;

public interface NoteKeeperHandler {

    Note create(String body);

    Optional<Note> getNote(Long id);

}
