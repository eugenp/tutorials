package com.baeldung.eval.cdi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Author {
    Computer myComputer;

    @Autowired
    public Author(Computer myComputer) {
        this.myComputer = myComputer;
    }

    public void startDraft() {
        myComputer.startTyping();
    }

}
