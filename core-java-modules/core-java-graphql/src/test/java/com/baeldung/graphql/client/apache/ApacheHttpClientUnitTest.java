package com.baeldung.graphql.client.apache;

import com.baeldung.graphql.GraphQLMockServer;
import com.baeldung.graphql.data.Response;
import com.baeldung.graphql.generated.Book;
import com.baeldung.graphql.generated.util.QueryExecutor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graphql_java_generator.exception.GraphQLRequestExecutionException;
import com.graphql_java_generator.exception.GraphQLRequestPreparationException;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ApacheHttpClientUnitTest extends GraphQLMockServer {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void givenGraphQLEndpoint_whenRequestingAllBooksWithTitle_thenExpectedJsonIsReturned() throws IOException, URISyntaxException {
        HttpResponse httpResponse = ApacheHttpClient.callGraphQLService(serviceUrl, "{allBooks{title}}");
        String actualResponse = IOUtils.toString(httpResponse.getEntity().getContent(), StandardCharsets.UTF_8.name());
        Response parsedResponse = objectMapper.readValue(actualResponse, Response.class);

        assertAll(
            () -> assertThat(parsedResponse.getData().getAllBooks()).hasSize(2),
            () -> assertThat(parsedResponse.getData().getAllBooks().get(0).getTitle()).isEqualTo("Title 1"),
            () -> assertThat(parsedResponse.getData().getAllBooks().get(1).getTitle()).isEqualTo("Title 2")
        );
    }

    @Test
    void givenGraphQLEndpoint_whenRequestingAllBooksWithTitleAndAuthor_thenExpectedJsonIsReturned() throws IOException, URISyntaxException {
        HttpResponse httpResponse = ApacheHttpClient.callGraphQLService(serviceUrl, "{allBooks{title,author{name,surname}}}");
        String actualResponse = IOUtils.toString(httpResponse.getEntity().getContent(), StandardCharsets.UTF_8.name());
        Response parsedResponse = objectMapper.readValue(actualResponse, Response.class);

        assertAll(
          () -> assertThat(parsedResponse.getData().getAllBooks()).hasSize(2),
          () -> assertThat(parsedResponse.getData().getAllBooks().get(0).getTitle()).isEqualTo("Title 1"),
          () -> assertThat(parsedResponse.getData().getAllBooks().get(0).getAuthor().getFullName()).isEqualTo("Pero Peric"),
          () -> assertThat(parsedResponse.getData().getAllBooks().get(1).getTitle()).isEqualTo("Title 2"),
          () -> assertThat(parsedResponse.getData().getAllBooks().get(1).getAuthor().getFullName()).isEqualTo("Marko Maric")
        );
    }

    @Test
    void asd() throws GraphQLRequestPreparationException, GraphQLRequestExecutionException {
        QueryExecutor queryExecutor = new QueryExecutor(serviceUrl);
        List<Book> books = queryExecutor.allBooks("{title}");

        assertThat(books).hasSize(2);
    }

}
