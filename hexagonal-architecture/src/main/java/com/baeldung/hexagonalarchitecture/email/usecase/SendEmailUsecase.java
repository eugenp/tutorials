package com.baeldung.hexagonalarchitecture.email.usecase;

import com.baeldung.hexagonalarchitecture.book.usecase.dto.BookDto;
import com.baeldung.hexagonalarchitecture.email.port.outbound.EmailPort;
import com.baeldung.hexagonalarchitecture.notification.usecase.ReaderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendEmailUsecase {

    private final EmailPort emailPort;

    public void send(ReaderDto reader, BookDto book) {

        emailPort.send(reader.getEmail(), generateMessage(reader, book));
    }

    private String generateMessage(ReaderDto reader, BookDto book) {
        return "Dear " + reader.getName() + ",<br/>The book "
                + book.getName() + " has appeared in the library";
    }
}
