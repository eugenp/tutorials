package com.baeldung.graphql;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class DemoUnitTest extends GraphQLMockServer {

    @Test
    void givenGraphQLEndpoint_whenRequestingAllBooksWithTitle_thenExpectedJsonIsReturned() throws IOException, URISyntaxException {
        String expectedResponse = "{\"data\":{\"allBooks\":[{\"title\":\"Title 1\"},{\"title\":\"Title 2\"}]}}";

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet("http://" + SERVER_ADDRESS + ":" + serverPort + PATH);
        URI uri = new URIBuilder(request.getURI())
          .addParameter(QUERY_PARAMETER, "{allBooks{title}}")
          .build();
        request.setURI(uri);
        HttpResponse response = client.execute(request);

        assertThat(IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8.name())).isEqualTo(expectedResponse);
    }

    @Test
    void givenGraphQLEndpoint_whenRequestingAllBooksWithTitleAndAuthor_thenExpectedJsonIsReturned() throws IOException, URISyntaxException {
        String expectedResponse = "{\"data\":{\"allBooks\":[{\"title\":\"Title 1\",\"author\":\"Author 1\"},{\"title\":\"Title 2\",\"author\":\"Author 2\"}]}}";

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet("http://" + SERVER_ADDRESS + ":" + serverPort + PATH);
        URI uri = new URIBuilder(request.getURI())
          .addParameter(QUERY_PARAMETER, "{allBooks{title,author}}")
          .build();
        request.setURI(uri);
        HttpResponse response = client.execute(request);

        assertThat(IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8.name())).isEqualTo(expectedResponse);
    }

}
