package com.baeldung.note;

import com.baeldung.note.businesslogic.LoadNotePort;
import com.baeldung.note.businesslogic.DisplayNoteUseCase;
import com.baeldung.note.businesslogic.NoteReader;
import com.baeldung.note.serverside.NotePortPersistenceAdapter;
import com.baeldung.note.userside.ConsoleAdapter;

public class Program {
    public static void main(String[] args) {
        LoadNotePort loadNotePort=new NotePortPersistenceAdapter();

        DisplayNoteUseCase displayNoteUseCase =new NoteReader(loadNotePort);

        ConsoleAdapter consoleAdapter=new ConsoleAdapter(displayNoteUseCase);
        consoleAdapter.displayNotes();
    }
}
