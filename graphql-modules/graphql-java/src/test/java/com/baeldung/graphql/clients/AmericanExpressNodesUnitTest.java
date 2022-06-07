package com.baeldung.graphql.clients;

import com.baeldung.graphql.GraphQLMockServer;
import com.baeldung.graphql.data.Data;
import io.aexp.nodes.graphql.GraphQLResponseEntity;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class AmericanExpressNodesUnitTest extends GraphQLMockServer {

    @Test
    void givenGraphQLEndpoint_whenRequestingAllBooksWithTitle_thenExpectedJsonIsReturned() throws IOException {
        GraphQLResponseEntity<Data> responseEntity = AmericanExpressNodes.callGraphQLService(serviceUrl, "{allBooks{title}}");

        assertAll(
          () -> assertThat(responseEntity.getResponse().getAllBooks()).hasSize(2),
          () -> assertThat(responseEntity.getResponse().getAllBooks().get(0).getTitle()).isEqualTo("Title 1"),
          () -> assertThat(responseEntity.getResponse().getAllBooks().get(1).getTitle()).isEqualTo("Title 2")
        );
    }

    @Test
    void givenGraphQLEndpoint_whenRequestingAllBooksWithTitleAndAuthor_thenExpectedJsonIsReturned() throws IOException {
        GraphQLResponseEntity<Data> responseEntity = AmericanExpressNodes.callGraphQLService(serviceUrl, "{allBooks{title,author{name,surname}}}");

        assertAll(
          () -> assertThat(responseEntity.getResponse().getAllBooks()).hasSize(2),
          () -> assertThat(responseEntity.getResponse().getAllBooks().get(0).getTitle()).isEqualTo("Title 1"),
          () -> assertThat(responseEntity.getResponse().getAllBooks().get(0).getAuthor().getFullName()).isEqualTo("Pero Peric"),
          () -> assertThat(responseEntity.getResponse().getAllBooks().get(1).getTitle()).isEqualTo("Title 2"),
          () -> assertThat(responseEntity.getResponse().getAllBooks().get(1).getAuthor().getFullName()).isEqualTo("Marko Maric")
        );
    }

}
