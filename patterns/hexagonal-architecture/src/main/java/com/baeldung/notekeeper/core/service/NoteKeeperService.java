package com.baeldung.notekeeper.core.service;

import com.baeldung.notekeeper.core.model.Note;

import javax.annotation.Priority;
import java.util.Optional;

public interface NoteKeeperService {

    Note create(String body);

    Optional<Note> getNote(Long id);

}
