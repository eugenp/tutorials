package com.baeldung.hexagonalarchitecture.book.usecase;

import com.baeldung.hexagonalarchitecture.book.port.outbound.notification.NotifyReadersPort;
import com.baeldung.hexagonalarchitecture.book.port.outbound.persistence.BookEntity;
import com.baeldung.hexagonalarchitecture.book.port.outbound.persistence.BookRepository;
import com.baeldung.hexagonalarchitecture.book.usecase.dto.BookDto;
import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class CreateBookUsecase {

    BookRepository bookRepository;
    NotifyReadersPort notifyReadersPort;
    ModelMapper modelMapper;

    public void create(BookDto book) {
        bookRepository.save(modelMapper.map(book, BookEntity.class));
        notifyReadersPort.notify(book);
    }
}
