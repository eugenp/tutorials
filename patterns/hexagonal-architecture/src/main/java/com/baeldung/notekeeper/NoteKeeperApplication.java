package com.baeldung.notekeeper;

import com.baeldung.notekeeper.core.model.Note;
import com.baeldung.notekeeper.core.service.NoteKeeperService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Optional;

@SpringBootApplication
public class NoteKeeperApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(NoteKeeperApplication.class, args);

        NoteKeeperService noteKeeperService = ctx.getBean(NoteKeeperService.class);

        // Add new note
        Note helloWorldNote = noteKeeperService.create("Hello World!");

        // Get previously added note
        Optional<Note> note = noteKeeperService.getNote(helloWorldNote.getId());

        System.out.println("Got: " + note.get());

    }

}

