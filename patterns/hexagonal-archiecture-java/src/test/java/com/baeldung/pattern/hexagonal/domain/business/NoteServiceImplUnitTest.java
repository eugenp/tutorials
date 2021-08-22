package com.baeldung.pattern.hexagonal.domain.business;

import com.baeldung.pattern.hexagonal.domain.dto.NoteDTO;
import com.baeldung.pattern.hexagonal.domain.entities.Note;
import com.baeldung.pattern.hexagonal.persistence.NotesRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

public class NoteServiceImplUnitTest {

    private NotesRepository notesRepository;
    private NoteService noteService;

    @Before
    public void setup() {
      notesRepository = Mockito.mock(NotesRepository.class);
      noteService = new NoteServiceImpl(notesRepository);
    }

    @Test
    public void when_AddNote() {
        Mockito
          .when(notesRepository.addNote(Mockito.any(NoteDTO.class)))
          .then(invocationOnMock -> {
              NoteDTO noteDTO = invocationOnMock.getArgument(0, NoteDTO.class);
              return new Note(noteDTO.getTitle(), noteDTO.getContent());
          });

        NoteDTO noteDTO = new NoteDTO();
        noteDTO.setTitle("Note title");
        noteDTO.setContent("Note content goes here.");

        NoteDTO result = noteService.addNote(noteDTO);

        assertEquals(noteDTO.getTitle(), result.getTitle());
        assertEquals(noteDTO.getContent(), result.getContent());

        Mockito
          .verify(notesRepository, Mockito.times(1))
          .addNote(Mockito.any(NoteDTO.class));
    }

    @Test
    public void when_GetNote() {
        Note note = new Note("Title", "Content...");
        Mockito
          .when(notesRepository.getNote(Mockito.anyString()))
          .thenReturn(note);

        NoteDTO result = noteService.getNote("10");
        assertEquals(note.getTitle(), result.getTitle());
        assertEquals(note.getContent(), result.getContent());

        Mockito
          .verify(notesRepository, Mockito.times(1))
          .getNote(Mockito.anyString());
    }

}
