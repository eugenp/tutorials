package com.baeldung.hexagonalarchitecture.notification.port.inbound;

import com.baeldung.hexagonalarchitecture.book.port.outbound.notification.NotifyReadersPort;
import com.baeldung.hexagonalarchitecture.book.usecase.dto.BookDto;
import com.baeldung.hexagonalarchitecture.notification.usecase.NotificationUsecase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotifyReadersAdapter implements NotifyReadersPort {

    NotificationUsecase notificationUsecase;

    @Override
    public void notify(BookDto book) {
        notificationUsecase.notify(book);
    }
}
