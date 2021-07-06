package com.baeldung.hexagonalarchitecture.notification.port.outbound;

import com.baeldung.hexagonalarchitecture.book.usecase.dto.BookDto;
import com.baeldung.hexagonalarchitecture.notification.usecase.ReaderDto;

public interface Sender {
    void send(ReaderDto reader, BookDto book);
}
