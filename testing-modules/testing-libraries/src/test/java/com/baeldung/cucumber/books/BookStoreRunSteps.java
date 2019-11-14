package com.baeldung.cucumber.books;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.datatable.DataTable;

public class BookStoreRunSteps {

    private BookStore store;
    private List<Book> foundBooks;
    
    @Before
    public void setUp() {
        store = new BookStore();
        foundBooks = new ArrayList<>();
    }
    
    @Given("^I have the following books in the store by list$")
    public void haveBooksInTheStoreByList(DataTable table) {
        
        List<List<String>> rows = table.asLists(String.class);
        
        for (List<String> fields: rows) {
            store.addBook(new Book(fields.get(0), fields.get(1)));
        }
    }
    
    @Given("^I have the following books in the store by map$")
    public void haveBooksInTheStoreByMap(DataTable table) {
        
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);
        
        for (Map<String, String> fields: rows) {
            store.addBook(new Book(fields.get("title"), fields.get("author")));
        }
    }
    
    @Given("^I have the following books in the store with custom table parsing$")
    public void haveBooksInTheStoreByListOfDomainObjects(BookCatalog catalog) {
        store.addAllBooks(catalog.getBooks());
    }
    
    @When("^I search for books by author (.+)$")
    public void searchForBooksByAuthor(String author) {
        foundBooks = store.booksByAuthor(author);
    }
    
    @Then("^I find (\\d+) books$")
    public void findBooks(int count) {
        assertEquals(count, foundBooks.size());
    }
}
