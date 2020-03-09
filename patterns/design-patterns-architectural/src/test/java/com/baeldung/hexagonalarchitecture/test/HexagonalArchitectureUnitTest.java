package com.baeldung.hexagonalarchitecture.test;

import com.baeldung.hexagonalarchitecture.adapters.NoteListReaderAdapter;
import com.baeldung.hexagonalarchitecture.adapters.NoteListRepoAdapter;
import com.baeldung.hexagonalarchitecture.adapters.NoteListWriterAdapter;
import com.baeldung.hexagonalarchitecture.domain.Note;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HexagonalArchitectureUnitTest {

        @Test
        public void givenAdapters_whenDomainCalled_thenTwoAssertions() {
                NoteListRepoAdapter repo = new NoteListRepoAdapter();
                NoteListReaderAdapter reader = new NoteListReaderAdapter();
                NoteListWriterAdapter writer = new NoteListWriterAdapter();

                writer.write("test", repo);
                List<Note> notes = reader.read(repo);

                assertThat(notes.size()).isEqualTo(1);
                assertThat(notes.get(0).getNumCharacters()).isEqualTo(4);
        }
}
