package com.baeldung.hexagonalarchitecture.notification.usecase;

import com.baeldung.hexagonalarchitecture.book.usecase.dto.BookDto;
import com.baeldung.hexagonalarchitecture.notification.port.outbound.Sender;
import com.baeldung.hexagonalarchitecture.notification.port.outbound.persistence.ReaderRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@AllArgsConstructor
public class NotificationUsecase {

    Sender[] senders;
    ReaderRepository readerRepository;
    ModelMapper modelMapper;

    public void notify(BookDto book) {
        readerRepository.findAll()
                .stream()
                .map(b -> modelMapper.map(b, ReaderDto.class))
                .forEach(r -> this.notifyReader(r, book));
    }

    private void notifyReader(ReaderDto reader, BookDto book) {
        Arrays.stream(senders).forEach(s -> s.send(reader, book));
    }
}
