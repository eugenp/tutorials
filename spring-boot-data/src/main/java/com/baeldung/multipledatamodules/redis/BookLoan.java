package com.baeldung.multipledatamodules.redis;


import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("bookloans")
public class BookLoan {

    @Id
    private String bookId;
    
    private String loaner;
    private String loanDate;
    private String loanReturn;

    public BookLoan(String bookId, String loaner, String loanDate, String loanReturn) {
        super();
        this.bookId = bookId;
        this.loaner = loaner;
        this.loanDate = loanDate;
        this.loanReturn = loanReturn;
    }

    public String getBookId() {
        return bookId;
    }

    public String getLoaner() {
        return loaner;
    }

    public String getLoanDate() {
        return loanDate;
    }

    public String getLoanReturn() {
        return loanReturn;
    }

}
