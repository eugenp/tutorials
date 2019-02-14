package com.baeldung.notekeeper;

import com.baeldung.notekeeper.adapter.handler.BatchNoteKeeperHandler;
import com.baeldung.notekeeper.core.model.Note;
import com.baeldung.notekeeper.core.port.handler.NoteKeeperHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class NoteKeeperApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(NoteKeeperApplication.class, args);

        // Primary adapter
        BatchNoteKeeperHandler batchHandler = ctx.getBean(BatchNoteKeeperHandler.class);

        // Add new notes
        Collection<Note> notes = batchHandler.createNotes(Arrays.asList("first", "second", "third"));

        // Get saved notes' ids
        List<Long> savedNotesIds = notes.stream()
                .map(n -> n.getId())
                .collect(Collectors.toList());

        // Get saved notes
        Collection<Note> retrievedNotes = batchHandler.getNotes(savedNotesIds);
    }

}

