package com.baeldung.hexagonal.adapter;

import com.baeldung.hexagonal.HexagonalApplication;
import com.baeldung.hexagonal.domain.Book;
import com.baeldung.hexagonal.repository.BookEntity;
import com.baeldung.hexagonal.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = { HexagonalApplication.class})
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository repository;

    @Autowired
    private TestRestTemplate restTemplate;

    ObjectMapper objectMapper = new ObjectMapper();

    @After
    public void resetDb() {
        repository.deleteAll();
    }

    @Test
    public void whenValidInput_thenCreateBook() throws IOException, Exception {
        // assign - given
        Book book = new Book("java", "abc", 100);

        // act - when
        ResultActions result = mockMvc.perform(post("/book").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(book)));

        // assert - then
        result.andExpect(status().isCreated());
        List<BookEntity> found = repository.findAll();
        assertThat(found).extracting(BookEntity::getName)
            .containsOnly("java");
    }

    @Test
    public void givenBooks_whenGetBook_thenStatus200() throws IOException, Exception {
        // assign - given
        createTestBook("java", "abc", 100);
        createTestBook("c++", "xyz", 200);

        // act - when
        ResultActions result = mockMvc.perform(get("/book/java").accept(MediaType.APPLICATION_JSON));

        // assert - then
        result.andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name", is("java")))
            .andExpect(jsonPath("$.author", is("abc")))
            .andExpect(jsonPath("$.price", is(100)));
    }

    private void createTestBook(String name, String author, int price) {
        BookEntity book = new BookEntity(name, author, price);
        repository.saveAndFlush(book);
    }
}
