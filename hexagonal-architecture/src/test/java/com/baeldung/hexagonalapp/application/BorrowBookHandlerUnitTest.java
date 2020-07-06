package com.baeldung.hexagonalapp.application;

import com.baeldung.hexagonalapp.application.BorrowBookUseCase.BorrowBookCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class BorrowBookHandlerUnitTest {

    private BorrowBookHandler handler;

    @Mock
    private BookRepositoryPort bookRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        handler = new BorrowBookHandler(bookRepository);
    }

    @Test
    void whenBorrowingABook_thenTheBorrowerIsPersisted() {
        BookDto bookDto = new BookDto(1L, "Moby Dick", "Herman Melville");
        when(bookRepository.find(anyLong())).thenReturn(Optional.of(bookDto));

        BorrowBookCommand command = new BorrowBookCommand(1L, "John Doe");
        handler.handle(command);

        ArgumentCaptor<BookDto> captor = ArgumentCaptor.forClass(BookDto.class);
        verify(bookRepository, times(1)).save(captor.capture());
        assertThat(captor.getValue().getBorrower()).isEqualTo("John Doe");
    }

    @Test
    void whenBorrowingABookThatDoesNotExist_thenAnExceptionIsThrown() {
        when(bookRepository.find(anyLong())).thenReturn(Optional.empty());

        BorrowBookCommand command = new BorrowBookCommand(1L, "John Doe");
        assertThatThrownBy(() -> handler.handle(command)).isInstanceOf(BookNotFound.class);
    }
}