package com.baeldung.notekeeper.core.notifier;

import com.baeldung.notekeeper.core.model.Note;

public interface NoteNotifier {

    void sendNotification(Note note);

}
