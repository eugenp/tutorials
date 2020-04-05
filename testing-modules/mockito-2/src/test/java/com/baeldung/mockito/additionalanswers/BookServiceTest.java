package com.baeldung.additionalanswers;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {
    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookBusinessLogic bookBusinessLogic;

    @Test
    public void givenSaveMethodMocked_whenSaveInvoked_ThenReturnFirstArgument(){
        Book book = new Book("To Kill a Mocking Bird",  "Harper Lee", 256);
        Mockito.when(bookRepository.save(any(Book.class))).then(AdditionalAnswers.returnsFirstArg());

        Book savedBook = bookService.save(book);

        Assertions.assertThat(savedBook).isEqualTo(book);
    }
    @Test
    public void givenCheckifEqualsMethodMocked_whenCheckifEqualsInvoked_ThenReturnSecondArgument(){
        Book book1 = new Book(1L,"The Stranger","Albert Camus", 456);
        Book book2 = new Book(2L,"Animal Farm","George Orwell", 300);
        Book book3 = new Book(3L,"Romeo and Juliet","William Shakespeare", 200);

        Mockito.when(bookBusinessLogic.checkIfEquals(any(Book.class), any(Book.class), any(Book.class))).then(AdditionalAnswers.returnsSecondArg());

        Book secondBook = bookService.checkifEquals(book1, book2, book3);

        Assertions.assertThat(book2).isEqualTo(secondBook);
    }
    @Test
    public void givenCheckifEqualsMethodMocked_whenCheckifEqualsInvoked_ThenReturnLastArgument(){
        Book book1 = new Book(1L,"The Stranger","Albert Camus", 456);
        Book book2 = new Book(2L,"Animal Farm","George Orwell", 300);
        Book book3 = new Book(3L,"Romeo and Juliet","William Shakespeare", 200);

        Mockito.when(bookBusinessLogic.checkIfEquals(any(Book.class), any(Book.class), any(Book.class))).then(AdditionalAnswers.returnsLastArg());

        Book lastBook = bookService.checkifEquals(book1, book2, book3);

        Assertions.assertThat(book3).isEqualTo(lastBook);
    }
    @Test
    public void givenCheckifEqualsMethodMocked_whenCheckifEqualsInvoked_ThenReturnArgumentAtIndex(){
        Book book1 = new Book(1L,"The Stranger","Albert Camus", 456);
        Book book2 = new Book(2L,"Animal Farm","George Orwell", 300);
        Book book3 = new Book(3L,"Romeo and Juliet","William Shakespeare", 200);

        Mockito.when(bookBusinessLogic.checkIfEquals(any(Book.class), any(Book.class), any(Book.class))).then(AdditionalAnswers.returnsArgAt(1));

        Book bookOnIndex = bookService.checkifEquals(book1, book2, book3);

        Assertions.assertThat(book2).isEqualTo(bookOnIndex);
    }
}
