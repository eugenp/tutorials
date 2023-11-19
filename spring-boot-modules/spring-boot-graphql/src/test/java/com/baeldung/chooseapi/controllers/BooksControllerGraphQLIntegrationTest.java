package com.baeldung.chooseapi.controllers;

import com.baeldung.chooseapi.ChooseApiApp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.test.context.ActiveProfiles;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
  properties = { "grpc.server.port=-1" }, // Disable gRPC external server
  classes = ChooseApiApp.class)
@ActiveProfiles("chooseapi")
class BooksControllerGraphQLIntegrationTest {

    @Autowired
    private HttpGraphQlTester graphQlTester;

    @Test
    void givenBooksServiceThatReturnThreeBooks_whenCallingGraphQLEndpoint_thenThreeBooksAreReturned() throws Exception {
        String document = "query { books { title year author { firstName lastName }}}";
        Path expectedResponse = Paths.get("src/test/resources/graphql-test/books_expected_response.json");
        String expectedJson = new String(Files.readAllBytes(expectedResponse));

        this.graphQlTester.document(document)
          .execute()
          .path("books")
          .matchesJson(expectedJson);
    }

}
