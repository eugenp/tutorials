package com.baeldung.notekeeper.adapter.notifier;

import com.baeldung.notekeeper.core.model.Note;
import com.baeldung.notekeeper.core.notifier.NoteNotifier;
import org.springframework.stereotype.Component;

@Component
public class SmsNoteKeeperNotifier implements NoteNotifier {

    @Override
    public void sendNotification(Note note) {
        System.out.println("SMS notification for " + note + " sent.");
    }

}
