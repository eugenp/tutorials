package com.baeldung.note.userside;

import com.baeldung.note.businesslogic.DisplayNoteUseCase;

public class ConsoleAdapter {
    private DisplayNoteUseCase displayNoteUseCase;

    public ConsoleAdapter(DisplayNoteUseCase displayNoteUseCase) {
        this.displayNoteUseCase = displayNoteUseCase;
    }

    public void displayNotes() {
        displayNoteUseCase.display();
    }
}
