package com.baeldung.relationships;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.SpringDataRestApplication;
import com.baeldung.models.Address;
import com.baeldung.models.Author;
import com.baeldung.models.Book;
import com.baeldung.models.Library;

import org.junit.Test;
import static org.junit.Assert.*;

import org.json.JSONArray;
import org.json.JSONObject;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringDataRestApplication.class, webEnvironment = WebEnvironment.DEFINED_PORT)
public class SpringDataRelationshipsTest {

    @Autowired
    private TestRestTemplate template;

    private static final String BOOK_ENDPOINT = "http://localhost:8080/books/";
    private static final String AUTHOR_ENDPOINT = "http://localhost:8080/authors/";
    private static final String ADDRESS_ENDPOINT = "http://localhost:8080/addresses/";
    private static final String LIBRARY_ENDPOINT = "http://localhost:8080/libraries/";

    private static final String LIBRARY_NAME = "My Library";
    private static final String AUTHOR_NAME = "George Orwell";

    @Test
    public void whenSaveOneToOneRelationship_thenCorrect() {
        Library library = new Library(LIBRARY_NAME);
        ResponseEntity<Library> libraryPostResponse = template.postForEntity(LIBRARY_ENDPOINT, library, Library.class);
        assertEquals("status is not 201", HttpStatus.CREATED, libraryPostResponse.getStatusCode());

        Address address = new Address("Main street, nr 1");
        ResponseEntity<Address> addressPostResponse = template.postForEntity(ADDRESS_ENDPOINT, address, Address.class, "");
        assertEquals("status is not 201", HttpStatus.CREATED, addressPostResponse.getStatusCode());

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Content-type", "text/uri-list");
        HttpEntity<String> httpEntity = new HttpEntity<String>(ADDRESS_ENDPOINT + "/1", requestHeaders);
        template.exchange(LIBRARY_ENDPOINT + "/1/address", HttpMethod.PUT, httpEntity, String.class);

        ResponseEntity<Address> addressGetResponse = template.getForEntity(LIBRARY_ENDPOINT + "/1/address", Address.class);
        assertEquals("address is incorrect", addressGetResponse.getBody()
            .getLocation(),
            addressPostResponse.getBody()
                .getLocation());

        ResponseEntity<Library> libraryGetResponse = template.getForEntity(ADDRESS_ENDPOINT + "/1/library", Library.class);
        assertEquals("library is incorrect", libraryGetResponse.getBody()
            .getName(), LIBRARY_NAME);
    }

    @Test
    public void whenSaveOneToManyRelationship_thenCorrect() {
        Library library = new Library(LIBRARY_NAME);
        ResponseEntity<Library> libraryPostResponse = template.postForEntity(LIBRARY_ENDPOINT, library, Library.class);
        assertEquals("status is not 201", HttpStatus.CREATED, libraryPostResponse.getStatusCode());

        Book book1 = new Book("Dune");
        ResponseEntity<Book> book1PostResponse = template.postForEntity(BOOK_ENDPOINT, book1, Book.class, "");
        assertEquals("status is not 201", HttpStatus.CREATED, book1PostResponse.getStatusCode());

        Book book2 = new Book("1984");
        ResponseEntity<Book> book2PostResponse = template.postForEntity(BOOK_ENDPOINT, book2, Book.class, "");
        assertEquals("status is not 201", HttpStatus.CREATED, book2PostResponse.getStatusCode());

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Content-type", "text/uri-list");
        HttpEntity<String> httpEntity = new HttpEntity<String>(BOOK_ENDPOINT + "/1\n" + BOOK_ENDPOINT + "/2", requestHeaders);
        template.exchange(LIBRARY_ENDPOINT + "/1/books", HttpMethod.PUT, httpEntity, String.class);

        HttpEntity<String> bookHttpEntity = new HttpEntity<String>(LIBRARY_ENDPOINT + "/1", requestHeaders);
        template.exchange(BOOK_ENDPOINT + "/1/bookLibrary", HttpMethod.PUT, bookHttpEntity, String.class);
        template.exchange(BOOK_ENDPOINT + "/2/bookLibrary", HttpMethod.PUT, bookHttpEntity, String.class);

        ResponseEntity<Library> libraryGetResponse = template.getForEntity(BOOK_ENDPOINT + "/1/bookLibrary", Library.class);
        assertEquals("library is incorrect", libraryGetResponse.getBody()
            .getName(), LIBRARY_NAME);
    }

    @Test
    public void whenSaveManyToManyRelationship_thenCorrect() {
        Library library = new Library(LIBRARY_NAME);
        ResponseEntity<Library> libraryPostResponse = template.postForEntity(LIBRARY_ENDPOINT, library, Library.class);
        assertEquals("status is not 201", HttpStatus.CREATED, libraryPostResponse.getStatusCode());

        Author author1 = new Author(AUTHOR_NAME);
        ResponseEntity<Author> authorPostResponse = template.postForEntity(AUTHOR_ENDPOINT, author1, Author.class, "");
        assertEquals("status is not 201", HttpStatus.CREATED, authorPostResponse.getStatusCode());

        Book book1 = new Book("Animal Farm");
        ResponseEntity<Book> book1PostResponse = template.postForEntity(BOOK_ENDPOINT, book1, Book.class, "");
        assertEquals("status is not 201", HttpStatus.CREATED, book1PostResponse.getStatusCode());

        Book book2 = new Book("1984");
        ResponseEntity<Book> book2PostResponse = template.postForEntity(BOOK_ENDPOINT, book2, Book.class, "");
        assertEquals("status is not 201", HttpStatus.CREATED, book2PostResponse.getStatusCode());

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Content-type", "text/uri-list");
        HttpEntity<String> httpEntity = new HttpEntity<String>(BOOK_ENDPOINT + "/1\n" + BOOK_ENDPOINT + "/2", requestHeaders);
        template.exchange(AUTHOR_ENDPOINT + "/1/authorBooks", HttpMethod.PUT, httpEntity, String.class);

        String jsonResponse = template.getForObject(BOOK_ENDPOINT + "/1/bookAuthors", String.class);
        JSONObject jsonObj = new JSONObject(jsonResponse).getJSONObject("_embedded");
        JSONArray jsonArray = jsonObj.getJSONArray("authors");
        assertEquals("author is incorrect", jsonArray.getJSONObject(0)
            .getString("name"), AUTHOR_NAME);
    }
}
