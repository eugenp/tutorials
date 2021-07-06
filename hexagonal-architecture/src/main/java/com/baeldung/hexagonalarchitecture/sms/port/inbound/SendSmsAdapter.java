package com.baeldung.hexagonalarchitecture.sms.port.inbound;

import com.baeldung.hexagonalarchitecture.book.usecase.dto.BookDto;
import com.baeldung.hexagonalarchitecture.notification.port.outbound.Sender;
import com.baeldung.hexagonalarchitecture.notification.usecase.ReaderDto;
import com.baeldung.hexagonalarchitecture.sms.usecase.SendSmsUsecase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SendSmsAdapter implements Sender {

    SendSmsUsecase sendEmailUscase;

    @Override
    public void send(ReaderDto reader, BookDto book) {
        sendEmailUscase.send(reader, book);
    }
}
