package com.baeldung.hexagonalarchitecture.email.port.inbound;


import com.baeldung.hexagonalarchitecture.book.usecase.dto.BookDto;
import com.baeldung.hexagonalarchitecture.email.usecase.SendEmailUsecase;
import com.baeldung.hexagonalarchitecture.notification.port.outbound.Sender;
import com.baeldung.hexagonalarchitecture.notification.usecase.ReaderDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SendEmailAdapter implements Sender {

    SendEmailUsecase sendEmailUsecase;

    @Override
    public void send(ReaderDto reader, BookDto book) {
        sendEmailUsecase.send(reader, book);
    }
}
