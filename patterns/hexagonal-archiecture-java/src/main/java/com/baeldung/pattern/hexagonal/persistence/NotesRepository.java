package com.baeldung.pattern.hexagonal.persistence;

import com.baeldung.pattern.hexagonal.domain.dto.NoteDTO;
import com.baeldung.pattern.hexagonal.domain.entities.Note;
import org.springframework.stereotype.Repository;

@Repository
public interface NotesRepository {

    Note addNote(NoteDTO noteDTO);

    Note getNote(String noteId);

}
