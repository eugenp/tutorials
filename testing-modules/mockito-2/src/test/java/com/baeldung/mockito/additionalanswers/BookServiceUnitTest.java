package com.baeldung.mockito.additionalanswers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceUnitTest {
    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Test
    public void givenSaveMethodMocked_whenSaveInvoked_ThenReturnFirstArgument_UnitTest() {
        Book book = new Book("To Kill a Mocking Bird", "Harper Lee", 256);
        Mockito.when(bookRepository.save(any(Book.class))).then(AdditionalAnswers.returnsFirstArg());

        Book savedBook = bookService.save(book);

        assertEquals(savedBook, book);
    }

    @Test
    public void givenCheckifEqualsMethodMocked_whenCheckifEqualsInvoked_ThenReturnSecondArgument_UnitTest() {
        Book book1 = new Book(1L, "The Stranger", "Albert Camus", 456);
        Book book2 = new Book(2L, "Animal Farm", "George Orwell", 300);
        Book book3 = new Book(3L, "Romeo and Juliet", "William Shakespeare", 200);

        Mockito.when(bookRepository.checkIfEquals(any(Book.class), any(Book.class), any(Book.class))).then(AdditionalAnswers.returnsSecondArg());

        Book secondBook = bookService.checkifEquals(book1, book2, book3);

        assertEquals(secondBook, book2);
    }

    @Test
    public void givenCheckifEqualsMethodMocked_whenCheckifEqualsInvoked_ThenReturnLastArgument_UnitTest() {
        Book book1 = new Book(1L, "The Stranger", "Albert Camus", 456);
        Book book2 = new Book(2L, "Animal Farm", "George Orwell", 300);
        Book book3 = new Book(3L, "Romeo and Juliet", "William Shakespeare", 200);

        Mockito.when(bookRepository.checkIfEquals(any(Book.class), any(Book.class), any(Book.class))).then(AdditionalAnswers.returnsLastArg());

        Book lastBook = bookService.checkifEquals(book1, book2, book3);
        assertEquals(lastBook, book3);
    }

    @Test
    public void givenCheckifEqualsMethodMocked_whenCheckifEqualsInvoked_ThenReturnArgumentAtIndex_UnitTest() {
        Book book1 = new Book(1L, "The Stranger", "Albert Camus", 456);
        Book book2 = new Book(2L, "Animal Farm", "George Orwell", 300);
        Book book3 = new Book(3L, "Romeo and Juliet", "William Shakespeare", 200);

        Mockito.when(bookRepository.checkIfEquals(any(Book.class), any(Book.class), any(Book.class))).then(AdditionalAnswers.returnsArgAt(1));

        Book bookOnIndex = bookService.checkifEquals(book1, book2, book3);

        assertEquals(bookOnIndex, book2);
    }
}
