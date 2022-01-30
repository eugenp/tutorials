package com.baeldung.graphql.client.apache;

import com.baeldung.graphql.GraphQLMockServer;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class ApacheHttpClientUnitTest extends GraphQLMockServer {

    @Test
    void givenGraphQLEndpoint_whenRequestingAllBooksWithTitle_thenExpectedJsonIsReturned() throws IOException, URISyntaxException {
        String expectedResponse = "{\"data\":{\"allBooks\":[{\"title\":\"Title 1\"},{\"title\":\"Title 2\"}]}}";
        HttpResponse httpResponse = ApacheHttpClient.callGraphQLService(serviceUrl, "{allBooks{title}}");
        String actualResponse = IOUtils.toString(httpResponse.getEntity().getContent(), StandardCharsets.UTF_8.name());

        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    void givenGraphQLEndpoint_whenRequestingAllBooksWithTitleAndAuthor_thenExpectedJsonIsReturned() throws IOException, URISyntaxException {
        String expectedResponse = "{\"data\":{\"allBooks\":[{\"title\":\"Title 1\",\"author\":{\"name\":\"Pero\",\"surname\":\"Peric\"}},{\"title\":\"Title 2\",\"author\":{\"name\":\"Marko\",\"surname\":\"Maric\"}}]}}";
        HttpResponse httpResponse = ApacheHttpClient.callGraphQLService(serviceUrl, "{allBooks{title,author{name,surname}}}");
        String actualResponse = IOUtils.toString(httpResponse.getEntity().getContent(), StandardCharsets.UTF_8.name());

        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

}
