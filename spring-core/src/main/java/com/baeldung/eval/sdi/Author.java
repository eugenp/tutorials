package com.baeldung.eval.sdi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Author {
    Computer myComputer;

    public void startDraft() {
        myComputer.startTyping();
    }

    // Setter based bean injection
    @Autowired
    public void setComputer(Computer myComputer) {
        this.myComputer = myComputer;
    }

}
