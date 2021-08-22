package com.baeldung.pattern.hexagonal.domain.business;

import com.baeldung.pattern.hexagonal.domain.dto.NoteDTO;
import com.baeldung.pattern.hexagonal.domain.entities.Note;
import com.baeldung.pattern.hexagonal.persistence.NotesRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

@Component
public class NoteServiceImpl implements NoteService {

    private final NotesRepository notesRepository;

    public NoteServiceImpl(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }

    @Override
    public NoteDTO getNote(String noteId) {
        try {
            Note note = notesRepository.getNote(noteId);
            return new NoteDTO(note.getId(), note.getTitle(), note.getContent());
        } catch (EntityNotFoundException e) {
            return null;
        }
    }

    @Override
    public NoteDTO addNote(NoteDTO noteDTO) {
        Note note = notesRepository.addNote(noteDTO);
        return new NoteDTO(note.getId(), note.getTitle(), note.getContent());
    }
}
