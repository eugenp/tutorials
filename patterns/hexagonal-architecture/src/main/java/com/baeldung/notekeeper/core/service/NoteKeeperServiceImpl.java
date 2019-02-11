package com.baeldung.notekeeper.core.service;

import com.baeldung.notekeeper.core.model.Note;
import com.baeldung.notekeeper.core.notifier.NoteNotifier;
import com.baeldung.notekeeper.core.repository.NoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class NoteKeeperServiceImpl implements NoteKeeperService {

    private NoteRepository noteRepository;

    private NoteNotifier noteNotifier;

    public NoteKeeperServiceImpl(NoteRepository noteRepository, NoteNotifier noteNotifier) {
        this.noteRepository = noteRepository;
        this.noteNotifier = noteNotifier;
    }

    @Override
    public Note create(String body) {
        if (StringUtils.isEmpty(body)) {
            throw new IllegalArgumentException("Both, priority and note's body, must be provided!");
        }

        Note note = new Note(body);

        Note persistedNote = store(note);

        sendNotification(persistedNote);

        return persistedNote;
    }

    private void sendNotification(Note note) {
        this.noteNotifier.sendNotification(note);
    }

    private Note store(Note note) {
        return this.noteRepository.create(note);
    }

    @Override
    public Optional<Note> getNote(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(this.noteRepository.getById(id));
    }

}
