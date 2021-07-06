package com.baeldung.hexagonalarchitecture.book.port.outbound.notification;


import com.baeldung.hexagonalarchitecture.book.usecase.dto.BookDto;

public interface NotifyReadersPort {
    void notify(BookDto book);
}
