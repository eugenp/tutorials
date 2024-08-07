package com.baeldung.ddd.hexagonal.app.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.baeldung.ddd.hexagonal.app.adapters.in.web.LibraryController;
import com.baeldung.ddd.hexagonal.app.domain.Book;
import com.baeldung.ddd.hexagonal.app.domain.Rental;
import com.baeldung.ddd.hexagonal.app.ports.in.LibraryService;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(LibraryController.class)
public class LibraryControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LibraryService libraryService;

    private Book book;
    private Rental rental;
    private List<Book> bookList;

    @BeforeEach
    public void setUp() {
        book = new Book(1L, "Test Book", "Test Author");
        rental = new Rental(1L, book, LocalDate.now(), null, BigDecimal.ZERO);
        bookList = Arrays.asList(book);
    }

    @Test
    public void testRentBook() throws Exception {
        when(libraryService.rentBook(anyLong())).thenReturn(rental);

        ResultActions resultActions = mockMvc.perform(post("/library/books/1/rent")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        String responseString = resultActions.andReturn().getResponse().getContentAsString();
        Rental responseRental = new ObjectMapper().readValue(responseString, Rental.class);

        assertNotNull(responseRental);
        assertEquals(rental.getId(), responseRental.getId());
    }

    @Test
    public void testReturnBook() throws Exception {
        when(libraryService.returnBook(anyLong())).thenReturn(rental);

        ResultActions resultActions = mockMvc.perform(post("/library/rentals/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        String responseString = resultActions.andReturn().getResponse().getContentAsString();
        Rental responseRental = new ObjectMapper().readValue(responseString, Rental.class);

        assertNotNull(responseRental);
        assertEquals(rental.getId(), responseRental.getId());
    }

    @Test
    public void testCalculateFine() throws Exception {
        BigDecimal fine = BigDecimal.valueOf(10);
        when(libraryService.calculateFine(anyLong())).thenReturn(fine);

        mockMvc.perform(get("/library/rentals/1/fine")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(fine.toString()));
    }
}
