package com.baeldung.chooseapi.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootTest(properties = { "grpc.server.port=-1" }) // Disable gRPC external server
@ActiveProfiles("chooseapi")
@AutoConfigureMockMvc
class BooksControllerRestIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void givenBooksServiceThatReturnThreeBooks_whenCallingRestEndpoint_thenThreeBooksAreReturned() throws Exception {
        Path expectedResponse = Paths.get("src/test/resources/graphql-test/books_expected_response.json");
        String expectedJson = new String(Files.readAllBytes(expectedResponse));

        this.mockMvc.perform(get("/rest/books"))
          .andDo(print())
          .andExpect(status().isOk())
          .andExpect(content().json(expectedJson));
    }

}
