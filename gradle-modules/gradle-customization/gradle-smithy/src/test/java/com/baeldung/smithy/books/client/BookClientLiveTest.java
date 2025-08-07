package com.baeldung.smithy.books.client;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.baeldung.smithy.books.client.client.BookManagementServiceClient;
import com.baeldung.smithy.books.client.model.GetBookInput;
import com.baeldung.smithy.books.client.model.GetBookOutput;

import software.amazon.smithy.java.client.core.endpoint.EndpointResolver;

public class BookClientLiveTest {
    @Test
    @Disabled
    void whenCallingTheSmithyApi_thenTheCorrectResultIsReturned() {
        BookManagementServiceClient client = BookManagementServiceClient.builder()
            .endpointResolver(EndpointResolver.staticEndpoint("http://localhost:8888"))
            .build();

        GetBookOutput output = client.getBook(GetBookInput.builder()
            .bookId("abc123")
            .build());
        assertEquals("Head First Java, 3rd Edition: A Brain-Friendly Guide", output.title());
    }
}
