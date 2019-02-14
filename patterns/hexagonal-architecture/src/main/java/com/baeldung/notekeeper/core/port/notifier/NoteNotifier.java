package com.baeldung.notekeeper.core.port.notifier;

import com.baeldung.notekeeper.core.model.Note;

public interface NoteNotifier {

    void sendNotification(Note note);

}
