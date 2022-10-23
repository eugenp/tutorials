package com.baeldung.chooseapi.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.WebGraphQlTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BooksControllerGraphQLIntegrationTest {

    @Autowired
    private MockMvc mockMvc; //TODO: replace MockMvc with HttpGraphQlTester

    @Test
    void givenBooksServiceThatReturnThreeBooks_whenCallingGraphQLEndpoint_thenThreeBooksAreReturned() throws Exception {
        this.mockMvc.perform(post("/graphql")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"query\":\"{ books { title year author { firstName lastName } } }\"}"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(3)));
    }

}
