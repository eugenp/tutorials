package com.baeldung.patterns.hexagonal_quick.port;

import com.baeldung.patterns.hexagonal_quick.domain.BorrowRecord;
import com.baeldung.patterns.hexagonal_quick.domain.ReturnRecord;

public interface BorrowInputPort {
    BorrowRecord borrowBook(String isbn, String username);

    ReturnRecord returnBook(String borrowId, String isbn);
}