package com.baeldung.hexagonal.integration;

import com.baeldung.hexagonal.Application;
import com.baeldung.hexagonal.core.BookAuthorService;
import com.baeldung.hexagonal.domain.AuthorDto;
import com.baeldung.hexagonal.domain.BookDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = {Application.class})
public class ApiIntegrationTest extends Assert {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private BookAuthorService bookAuthorService;


    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void should_return_200_ok_when_get_books() throws Exception {
        List<BookDto> books=new ArrayList<>();
        List<AuthorDto> authors=new ArrayList<>();
        authors.add(AuthorDto.builder().name("Meysam").build());
        books.add(BookDto.builder().name("Book1").authors(authors).build());

        when(bookAuthorService.getAllBook()).thenReturn(books);

        MvcResult mvcResult = mockMvc.perform(get("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Meysam"));
    }

}
