package com.baeldung.cucumber.books;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.datatable.DataTable;

import static org.junit.Assert.*;

public class BookStoreRunSteps {
    private BookStore store;
    private List<Book> foundBooks;
    private Book foundBook;
    
    @Before
    public void setUp() {
        store = new BookStore();
        foundBooks = new ArrayList<>();
    }
    
    @Given("^I have the following books in the store$")
    public void haveBooksInTheStore(DataTable table) {
        haveBooksInTheStoreByList(table);
    }

    @Given("^I have the following books in the store by list$")
    public void haveBooksInTheStoreByList(DataTable table) {
        List<List<String>> rows = table.asLists(String.class);

        for (List<String> columns: rows) {
            store.addBook(new Book(columns.get(0), columns.get(1)));
        }
    }

    @Given("^I have the following books in the store by map$")
    public void haveBooksInTheStoreByMap(DataTable table) {
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);
        
        for (Map<String, String> columns: rows) {
            store.addBook(new Book(columns.get("title"), columns.get("author")));
        }
    }
    
    @Given("^I have the following books in the store with transformer$")
    public void haveBooksInTheStoreByTransformer(BookCatalog catalog) {
        store.addAllBooks(catalog.getBooks());
    }
    
    @When("^I search for books by author (.+)$")
    public void searchForBooksByAuthor(String author) {
        foundBooks = store.booksByAuthor(author);
    }

    @When("^I search for a book titled (.+)$")
    public void searchForBookByTitle(String title) {
        foundBook = store.bookByTitle(title).orElse(null);
    }
    
    @Then("^I find (\\d+) books$")
    public void findBooks(int count) {
        assertEquals(count, foundBooks.size());
    }

    @Then("^I find a book$")
    public void findABook() {
        assertNotNull(foundBook);
    }

    @Then("^I find no book$")
    public void findNoBook() {
        assertNull(foundBook);
    }
}
