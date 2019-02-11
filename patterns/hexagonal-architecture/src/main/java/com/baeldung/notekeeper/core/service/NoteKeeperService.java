package com.baeldung.notekeeper.core.service;

import com.baeldung.notekeeper.core.model.Note;

import java.util.Optional;

public interface NoteKeeperService {

    Note create(String body);

    Optional<Note> getNote(Long id);

}
