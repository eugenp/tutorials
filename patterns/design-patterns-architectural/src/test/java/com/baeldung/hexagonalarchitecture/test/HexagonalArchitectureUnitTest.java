package com.baeldung.hexagonalarchitecture.test;

import com.baeldung.hexagonalarchitecture.adapters.NoteListRepoAdapter;
import com.baeldung.hexagonalarchitecture.adapters.NoteListServiceAdapter;
import com.baeldung.hexagonalarchitecture.domain.Note;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HexagonalArchitectureUnitTest {

        @Test
        public void givenAdapters_whenDomainCalled_thenTwoAssertions() {

                NoteListRepoAdapter repo = new NoteListRepoAdapter();
                NoteListServiceAdapter noteService = new NoteListServiceAdapter(repo);

                noteService.write("test");
                List<Note> notes = noteService.read();

                assertThat(notes.size()).isEqualTo(1);
                assertThat(notes.get(0).getNumCharacters()).isEqualTo(4);
        }
}
