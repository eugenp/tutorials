package com.suri.blogic.adapters;

import com.suri.blogic.ports.LibraryTransactionsPort;
import com.suri.model.Book;
import com.suri.model.Member;
import com.suri.service.BookService;
import com.suri.service.MemberService;
import com.suri.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;

public class LibraryTransactionsAdapter implements LibraryTransactionsPort {

    private BookService bookService;
    private TransactionService transactionService;
    private MemberService memberService;

    @Autowired
    public LibraryTransactionsAdapter(TransactionService service,
                                      BookService bookService,
                                      MemberService memberService){
        this.transactionService = service;
        this.bookService = bookService;
        this.memberService = memberService;
    }

    public String issueBook(Book book, Member member) {
        return transactionService.issue(book, member);
    }

    public String returnBook(Book book) {
        return transactionService.returnBook(book);
    }

    public String queryBook(Book book) {
        return bookService.query(book);
    }

    public String queryMember(Member member) {
        return memberService.query(member);
    }
}
