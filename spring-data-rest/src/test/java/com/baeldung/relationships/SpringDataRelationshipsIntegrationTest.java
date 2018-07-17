package com.baeldung.relationships;

import static org.junit.Assert.assertEquals;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.SpringDataRestApplication;
import com.baeldung.models.Address;
import com.baeldung.models.Author;
import com.baeldung.models.Book;
import com.baeldung.models.Library;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringDataRestApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class SpringDataRelationshipsIntegrationTest {

    @Autowired
    private TestRestTemplate template;
    
    @Value("${local.server.port}")
    private int port;

    private static final String BOOK_ENDPOINT = "http://localhost:%s/books/";
    private static final String AUTHOR_ENDPOINT = "http://localhost:%s/authors/";
    private static final String ADDRESS_ENDPOINT = "http://localhost:%s/addresses/";
    private static final String LIBRARY_ENDPOINT = "http://localhost:%s/libraries/";

    private static final String LIBRARY_NAME = "My Library";
    private static final String AUTHOR_NAME = "George Orwell";

    @Test
    public void whenSaveOneToOneRelationship_thenCorrect() throws JSONException {
        Library library = new Library(LIBRARY_NAME);
        template.postForEntity(String.format(LIBRARY_ENDPOINT, port), library, Library.class);

        Address address = new Address("Main street, nr 1");
        template.postForEntity(String.format(ADDRESS_ENDPOINT, port), address, Address.class);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Content-type", "text/uri-list");
        HttpEntity<String> httpEntity = new HttpEntity<>(String.format(ADDRESS_ENDPOINT, port) + "/1", requestHeaders);
        template.exchange(String.format(LIBRARY_ENDPOINT, port) + "/1/libraryAddress", HttpMethod.PUT, httpEntity, String.class);

        ResponseEntity<Library> libraryGetResponse = template.getForEntity(String.format(ADDRESS_ENDPOINT, port) + "/1/library", Library.class);
        assertEquals("library is incorrect", libraryGetResponse.getBody()
            .getName(), LIBRARY_NAME);
    }

    @Test
    public void whenSaveOneToManyRelationship_thenCorrect() throws JSONException{
        Library library = new Library(LIBRARY_NAME);
        template.postForEntity(String.format(LIBRARY_ENDPOINT, port), library, Library.class);

        Book book1 = new Book("Dune");
        template.postForEntity(String.format(BOOK_ENDPOINT, port), book1, Book.class);

        Book book2 = new Book("1984");
        template.postForEntity(String.format(BOOK_ENDPOINT, port), book2, Book.class);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Content-type", "text/uri-list");
        HttpEntity<String> bookHttpEntity = new HttpEntity<>(String.format(LIBRARY_ENDPOINT, port) + "/1", requestHeaders);
        template.exchange(String.format(BOOK_ENDPOINT, port) + "/1/library", HttpMethod.PUT, bookHttpEntity, String.class);
        template.exchange(String.format(BOOK_ENDPOINT, port) + "/2/library", HttpMethod.PUT, bookHttpEntity, String.class);

        ResponseEntity<Library> libraryGetResponse = template.getForEntity(String.format(BOOK_ENDPOINT, port) + "/1/library", Library.class);
        assertEquals("library is incorrect", libraryGetResponse.getBody()
            .getName(), LIBRARY_NAME);
    }

    @Test
    public void whenSaveManyToManyRelationship_thenCorrect() throws JSONException{
        Author author1 = new Author(AUTHOR_NAME);
        template.postForEntity(String.format(AUTHOR_ENDPOINT, port), author1, Author.class);

        Book book1 = new Book("Animal Farm");
        template.postForEntity(String.format(BOOK_ENDPOINT, port), book1, Book.class);

        Book book2 = new Book("1984");
        template.postForEntity(String.format(BOOK_ENDPOINT, port), book2, Book.class);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Content-type", "text/uri-list");
        HttpEntity<String> httpEntity = new HttpEntity<>(String.format(BOOK_ENDPOINT, port) + "/1\n" + String.format(BOOK_ENDPOINT, port) + "/2", requestHeaders);
        template.exchange(String.format(AUTHOR_ENDPOINT, port) + "/1/books", HttpMethod.PUT, httpEntity, String.class);

        String jsonResponse = template.getForObject(String.format(BOOK_ENDPOINT, port) + "/1/authors", String.class);
        JSONObject jsonObj = new JSONObject(jsonResponse).getJSONObject("_embedded");
        JSONArray jsonArray = jsonObj.getJSONArray("authors");
        assertEquals("author is incorrect", jsonArray.getJSONObject(0)
            .getString("name"), AUTHOR_NAME);
    }
}
