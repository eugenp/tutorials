package com.baeldung.pattern.hexagonal.domain.business;

import com.baeldung.pattern.hexagonal.domain.dto.NoteDTO;
import org.springframework.stereotype.Component;

@Component
public interface NoteService {

    NoteDTO getNote(String noteId);

    NoteDTO addNote(NoteDTO noteDTO);

}
