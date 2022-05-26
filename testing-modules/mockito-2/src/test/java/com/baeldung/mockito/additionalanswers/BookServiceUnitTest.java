package com.baeldung.mockito.additionalanswers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyLong;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.VoidAnswer1;

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

        Mockito.when(bookRepository.selectRandomBook(any(Book.class), any(Book.class), any(Book.class))).then(AdditionalAnswers.returnsSecondArg());

        Book secondBook = bookService.selectRandomBook(book1, book2, book3);

        assertEquals(secondBook, book2);
    }

    @Test
    public void givenCheckifEqualsMethodMocked_whenCheckifEqualsInvoked_ThenReturnLastArgument_UnitTest() {
        Book book1 = new Book(1L, "The Stranger", "Albert Camus", 456);
        Book book2 = new Book(2L, "Animal Farm", "George Orwell", 300);
        Book book3 = new Book(3L, "Romeo and Juliet", "William Shakespeare", 200);

        Mockito.when(bookRepository.selectRandomBook(any(Book.class), any(Book.class), any(Book.class))).then(AdditionalAnswers.returnsLastArg());

        Book lastBook = bookService.selectRandomBook(book1, book2, book3);
        assertEquals(lastBook, book3);
    }

    @Test
    public void givenCheckifEqualsMethodMocked_whenCheckifEqualsInvoked_ThenReturnArgumentAtIndex_UnitTest() {
        Book book1 = new Book(1L, "The Stranger", "Albert Camus", 456);
        Book book2 = new Book(2L, "Animal Farm", "George Orwell", 300);
        Book book3 = new Book(3L, "Romeo and Juliet", "William Shakespeare", 200);

        Mockito.when(bookRepository.selectRandomBook(any(Book.class), any(Book.class), any(Book.class))).then(AdditionalAnswers.returnsArgAt(1));

        Book bookOnIndex = bookService.selectRandomBook(book1, book2, book3);

        assertEquals(bookOnIndex, book2);
    }

    @Test
    public void givenMockedMethod_whenMethodInvoked_thenReturnBook() {
        Long id = 1L;
        when(bookRepository.getByBookId(anyLong())).thenAnswer(AdditionalAnswers.answer((Long bookId) -> new Book(bookId, "The Stranger", "Albert Camus", 456)));

        assertNotNull(bookService.getByBookId(id));
        assertEquals("The Stranger", bookService.getByBookId(id).getTitle());
    }
    
    @Test
    public void givenMockedMethod_whenMethodInvoked_thenReturnVoid() {
        Long id = 2L;
        when(bookRepository.getByBookId(anyLong())).thenAnswer(AdditionalAnswers.answerVoid(new VoidAnswer1<Long>() {
            public void answer(Long id) {
                System.out.println(id);
            }
        }));
        
        bookService.getByBookId(id);
        
        verify(bookRepository, times(1)).getByBookId(id);
    }
}
