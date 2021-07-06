package com.baeldung.hexagonalarchitecture.sms.usecase;

import com.baeldung.hexagonalarchitecture.book.usecase.dto.BookDto;
import com.baeldung.hexagonalarchitecture.notification.usecase.ReaderDto;
import com.baeldung.hexagonalarchitecture.sms.port.outbound.SmsPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendSmsUsecase {

    private final SmsPort emailPort;

    public void send(ReaderDto reader, BookDto book) {
        emailPort.send(reader.getEmail(), generateMessage(book));
    }

    private String generateMessage(BookDto book) {
        return "The book " + book.getName() + " has appeared in the library";
    }
}
