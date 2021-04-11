package com.baeldung.hexagonalarchitecture.controller;

import com.baeldung.hexagonalarchitecture.HexagonalArchitectureApplication;
import com.baeldung.hexagonalarchitecture.models.Book;
import com.baeldung.hexagonalarchitecture.repositories.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = {HexagonalArchitectureApplication.class})
@WebAppConfiguration
@TestPropertySource(locations = "classpath:application.properties")
public class BookControllerIntegrationTest {

    public static final String BOOK_NAME = "Book Name";

    @Autowired
    private BookRepository repository;


    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;


    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }


    @Test
    public void testCreateBook() throws Exception {
        Book book = new Book();
        book.setId(1l);
        book.setName(BOOK_NAME);

        ObjectMapper mapper = new ObjectMapper();
        //Converting the Object to JSONString
        String bookString = mapper.writeValueAsString(book);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/create")
                .content(bookString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1l))
                .andExpect(jsonPath("$.name").value(book.getName()))
                .andReturn();
    }


    @Test
    public void testGetAllBooks() throws Exception {
        Book book = new Book();
        book.setId(1l);
        book.setName(BOOK_NAME);

        repository.save(book);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/list")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1l))
                .andExpect(jsonPath("$[0].name").value(book.getName()))
                .andReturn();
    }


}