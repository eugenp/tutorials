package com.baeldung.mockito.additionalanswers;

import org.springframework.stereotype.Service;

@Service
public class BookBusinessLogic {

    public Book checkIfEquals(Book bookOne, Book bookTwo, Book bookThree){
        if (bookOne.equals(bookTwo) && bookTwo.equals(bookThree) && bookThree.equals(bookOne)){
            return bookOne;
        }
        else return bookTwo;
    }
}
