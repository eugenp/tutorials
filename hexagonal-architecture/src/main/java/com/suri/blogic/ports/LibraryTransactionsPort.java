package com.suri.blogic.ports;

import com.suri.model.Book;
import com.suri.model.Member;

public interface LibraryTransactionsPort {
    String issueBook(Book book, Member member);
    String returnBook(Book book);
    String queryBook(Book book);
    String queryMember(Member member);
}
