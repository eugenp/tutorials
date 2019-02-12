package com.suri.service;

import com.suri.model.Book;
import com.suri.model.Member;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    public String issue(Book book, Member member) {
        return "issued";
    }

    public String returnBook(Book book) {
        return book.toString();
    }
}
