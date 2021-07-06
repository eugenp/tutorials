package com.baeldung.hexagonalarchitecture.usecase;

import com.baeldung.hexagonalarchitecture.book.port.outbound.notification.NotifyReadersPort;
import com.baeldung.hexagonalarchitecture.book.port.outbound.persistence.BookEntity;
import com.baeldung.hexagonalarchitecture.book.port.outbound.persistence.BookRepository;
import com.baeldung.hexagonalarchitecture.book.usecase.CreateBookUsecase;
import com.baeldung.hexagonalarchitecture.book.usecase.dto.BookDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class CreateBookUsecaseTest {

    @InjectMocks
    private CreateBookUsecase createBookUsecase;

    @Mock
    private BookDto book;

    @Mock
    private BookEntity bookEntity;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private NotifyReadersPort notifyReadersPort;

    @Test
    public void whenCreate_thenNotifyReaders() {
        // given
        Mockito.when(modelMapper.map(Mockito.eq(book), Mockito.eq(BookEntity.class)))
                .thenReturn(bookEntity);

        // when
        createBookUsecase.create(book);

        // then
        Mockito.verify(bookRepository, times(1))
                .save(Mockito.eq(bookEntity));
        Mockito.verify(notifyReadersPort, times(1))
                .notify(Mockito.eq(book));
    }
}
