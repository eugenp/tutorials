package com.baeldung.notekeeper.adapter.handler;

import com.baeldung.notekeeper.core.model.Note;
import com.baeldung.notekeeper.core.port.handler.NoteKeeperHandler;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class BatchNoteKeeperHandler {

    private NoteKeeperHandler noteKeeperHandler;

    public BatchNoteKeeperHandler(NoteKeeperHandler noteKeeperHandler) {
        this.noteKeeperHandler = noteKeeperHandler;
    }

    public Collection<Note> createNotes(Collection<String> bodies) {
        return bodies.stream()
                .map(b -> noteKeeperHandler.create(b))
                .collect(Collectors.toList());
    }

    public Collection<Note> getNotes(Collection<Long> ids) {
        return ids.stream()
                .map(i -> noteKeeperHandler.getNote(i))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

}
