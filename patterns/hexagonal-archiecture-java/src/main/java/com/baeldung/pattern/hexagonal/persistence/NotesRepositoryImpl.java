package com.baeldung.pattern.hexagonal.persistence;

import com.baeldung.pattern.hexagonal.domain.dto.NoteDTO;
import com.baeldung.pattern.hexagonal.domain.entities.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class NotesRepositoryImpl implements NotesRepository {

    private final NotesJpaRepository notesJpaRepository;

    public NotesRepositoryImpl(NotesJpaRepository notesJpaRepository) {
        this.notesJpaRepository = notesJpaRepository;
    }

    @Override
    public Note addNote(NoteDTO noteDTO) {
        Note note = new Note(noteDTO.getTitle(), noteDTO.getContent());
        return notesJpaRepository.save(note);
    }

    @Override
    public Note getNote(String noteId) {
        return notesJpaRepository.getById(Long.parseLong(noteId));
    }
}
