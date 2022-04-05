package com.baeldung.web.controller;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.baeldung.Application;
import com.baeldung.repository.BookRepository;
import com.baeldung.web.dto.Book;
import com.baeldung.web.error.ApiErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
@ComponentScan(basePackageClasses = Application.class)
public class BookControllerIntegrationTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookRepository repository;

    @Test
    public void givenNoExistingBooks_WhenGetBookWithId1_ThenErrorReturned() throws Exception {

        long id = 1;

        doReturn(Optional.empty()).when(repository).findById(eq(id));

        mvc.perform(get("/api/book/{id}", id))
            .andExpect(status().isNotFound())
            .andExpect(content().json(MAPPER.writeValueAsString(new ApiErrorResponse("error-0001", "No book found with ID " + id))));
    }

    @Test
    public void givenMatchingBookExists_WhenGetBookWithId1_ThenBookReturned() throws Exception {

        long id = 1;
        Book book = book(id, "foo", "bar");

        doReturn(Optional.of(book)).when(repository).findById(eq(id));

        mvc.perform(get("/api/book/{id}", id))
            .andExpect(status().isOk())
            .andExpect(content().json(MAPPER.writeValueAsString(book)));
    }

    private static Book book(long id, String title, String author) {

        Book book = new Book();

        book.setId(id);
        book.setTitle(title);
        book.setAuthor(author);

        return book;
    }
}