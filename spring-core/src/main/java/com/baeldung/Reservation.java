package com.baeldung;

import org.springframework.beans.factory.annotation.Autowired;

class Reservation {
    private Member member;
    private Ebook eBook;
    
    @Autowired
    public Reservation(Member member, Ebook eBook) {
        this.member = member;
        this.eBook = eBook;
    }  
}
