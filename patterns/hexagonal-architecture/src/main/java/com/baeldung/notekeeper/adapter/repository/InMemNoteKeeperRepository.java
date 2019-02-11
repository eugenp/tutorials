package com.baeldung.notekeeper.adapter.repository;

import com.baeldung.notekeeper.core.model.Note;
import com.baeldung.notekeeper.core.repository.NoteRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class InMemNoteKeeperRepository implements NoteRepository {

    private long currentId = 0L;
    private Map<Long, Note> inMemStorage = new HashMap<>();

    @Override
    public Note create(Note note) {
        if(note != null){
            note.setId(++currentId);
        }

        inMemStorage.put(currentId, note);

        return note;
    }

    @Override
    public Note getById(Long id) {
        return inMemStorage.get(id);
    }
}

