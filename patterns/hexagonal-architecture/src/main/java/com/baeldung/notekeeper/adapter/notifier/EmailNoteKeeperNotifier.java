package com.baeldung.notekeeper.adapter.notifier;

import com.baeldung.notekeeper.core.model.Note;
import com.baeldung.notekeeper.core.port.notifier.NoteNotifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class EmailNoteKeeperNotifier implements NoteNotifier {

    @Override
    public void sendNotification(Note note) {
        System.out.println("Email notification for " + note + " sent.");
    }

}
