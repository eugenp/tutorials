package com.baeldung.notekeeper.core.repository;

import com.baeldung.notekeeper.core.model.Note;

public interface NoteRepository {

    Note create(Note note);

    Note getById(Long id);

}
