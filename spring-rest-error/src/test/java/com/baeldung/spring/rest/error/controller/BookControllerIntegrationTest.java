package com.baeldung.spring.rest.error.controller;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.baeldung.spring.rest.error.domain.Book;
import com.baeldung.spring.rest.error.exception.BookNotFoundException;
import com.baeldung.spring.rest.error.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
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
            .andExpect(content().string(new BookNotFoundException(id).toJson()));
    }
    
    @Test
    public void givenMatchingBookExists_WhenGetBookWithId1_ThenBookReturned() throws Exception {
        
        long id = 1;
        Book book = book(id, "foo", "bar");
        
        doReturn(Optional.of(book)).when(repository).findById(eq(id));
        
        mvc.perform(get("/api/book/{id}", id))
            .andExpect(status().isOk())
            .andExpect(content().string(MAPPER.writeValueAsString(book)));
    }
    
    private static Book book(long id, String title, String author) {
        
        Book book = new Book();
        
        book.setId(id);
        book.setTitle(title);
        book.setAuthor(author);
        
        return book;
    }
}
