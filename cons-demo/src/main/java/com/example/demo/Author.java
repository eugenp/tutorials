package com.example.demo;

public class Author {
    Computer myComputer;

    public Author(Computer myComputer) {
        this.myComputer = myComputer;
    }

    public void startDraft() {
        myComputer.startTyping();
    }

}
