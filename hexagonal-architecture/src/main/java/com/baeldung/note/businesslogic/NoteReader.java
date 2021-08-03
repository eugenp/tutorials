package com.baeldung.note.businesslogic;

public class NoteReader implements DisplayNoteUseCase {

    private LoadNotePort obtainNote;

    public NoteReader(LoadNotePort obtainNote) {
        this.obtainNote = obtainNote;
    }

    @Override
    public void display(){
        obtainNote.findAll().stream().map(Note::format).forEach(System.out::println);
    }
}
