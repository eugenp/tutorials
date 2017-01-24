package com.baeldung;

import org.springframework.beans.factory.annotation.Autowired;

public class Reservation {
    private Member memebr;
    private Ebook eBook;
    
    @Autowired
    public Reservation(Member memebr, Ebook eBook) {
        this.memebr = memebr;
        this.eBook = eBook;
    }  
}
