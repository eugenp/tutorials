package com.example.demo;

public class Author {
    Computer myComputer;

    public void startDraft() {
        myComputer.startTyping();
    }

    public void setComputer(Computer myComputer) {
        this.myComputer = myComputer;
    }

}
