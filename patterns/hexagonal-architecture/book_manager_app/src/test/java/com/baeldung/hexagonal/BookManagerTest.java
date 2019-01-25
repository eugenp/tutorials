package com.baeldung.hexagonal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.hexagonal.domain.Book;
import com.baeldung.hexagonal.domain.Inventory;
import com.baeldung.hexagonal.exception.BookNotFoundException;
import com.baeldung.hexagonal.repository.BookRepository;
import com.baeldung.hexagonal.repository.InventoryRepository;
import com.baeldung.hexagonal.service.BookManager;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookManagerTest {

    @MockBean
    private BookRepository bookRepoMock;

    @MockBean
    private InventoryRepository inventoryRepoMock;

    @Autowired
    @Qualifier("BookManagerImpl")
    private BookManager manager;

    private Book book = null;
    private Inventory inventory = null;

    @Before
    public void setup() {
        book = new Book("MockISBN", "MockName", "MockAuthor");
        inventory = new Inventory(1, 1, book);
    }

    @Test
    public void testAddBook() {
        Mockito.when(bookRepoMock.save(book))
            .thenReturn(book);
        Mockito.when(inventoryRepoMock.save(inventory))
            .thenReturn(inventory);
        assertThat(manager.addBook(book)).isEqualTo(book);
    }

    @Test
    public void testFindBookByIsbnForExistingBook() {
        Mockito.when(bookRepoMock.findBookByIsbn("MockISBN"))
            .thenReturn(Optional.of(book));
        String isbn = "MockISBN";
        try {
            assertThat(manager.findBookByIsbn(isbn)).isEqualTo(book);
        } catch (BookNotFoundException e) {
            assertFalse(e.getMessage(), true);
        }
    }

    @Test
    public void testFindBookByIsbnForNonExistingBook() {
        Mockito.when(bookRepoMock.findBookByIsbn("MockISBN"))
            .thenReturn(Optional.of(book));
        String isbn = "InvalidISBN";
        try {
            assertThat(manager.findBookByIsbn(isbn)).isNull();
        } catch (BookNotFoundException e) {
            assertTrue(e.getMessage(), true);
        }
    }

    @Test
    public void testDeleteBook() {
        Book deletedBook = book;
        Mockito.when(inventoryRepoMock.findInventoryByBookIsbn("MockISBN"))
            .thenReturn(Optional.of(inventory));
        String isbn = "MockISBN";
        try {
            assertThat(manager.deleteBook(isbn)).isEqualTo(deletedBook);
        } catch (BookNotFoundException e) {
            assertFalse(e.getMessage(), true);
        }
    }

    @Test
    public void testFetchAllBooks() {
        List<Book> bookList = new ArrayList<>();
        bookList.add(book);
        Mockito.when(bookRepoMock.findAll())
            .thenReturn(bookList);
        assertThat(manager.fetchAllBooks()).hasSize(1);
    }

    @Test
    public void testUpdateBook() {
        Mockito.when(bookRepoMock.save(book))
            .thenReturn(book);
        Mockito.when(bookRepoMock.findBookByIsbn("MockISBN"))
            .thenReturn(Optional.of(book));
        Book updatedBook = book;
        updatedBook.setName("UpdatedMockName");
        try {
            assertThat(manager.updateBook(updatedBook)).hasFieldOrPropertyWithValue("name", "UpdatedMockName");
        } catch (BookNotFoundException e) {
            assertFalse(e.getMessage(), true);
        }
    }
}
