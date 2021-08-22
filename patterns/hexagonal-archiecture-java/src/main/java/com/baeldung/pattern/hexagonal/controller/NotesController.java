package com.baeldung.pattern.hexagonal.controller;

import com.baeldung.pattern.hexagonal.domain.business.NoteService;
import com.baeldung.pattern.hexagonal.domain.dto.NoteDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class NotesController implements NotesApi {

    private final NoteService noteService;

    public NotesController(NoteService noteService) {
        this.noteService = noteService;
    }

    @Override
    public NoteDTO getNote(String noteId) {
        NoteDTO note = noteService.getNote(noteId);
        if(note == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Note doesn't exist");
        }
        return note;
    }

    @Override
    public NoteDTO addNote(NoteDTO noteDTO) {
        return noteService.addNote(noteDTO);
    }
}
