package com.baeldung.moco;

import com.github.dreamhead.moco.HttpServer;
import com.github.dreamhead.moco.junit.MocoJunitRunner;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.github.dreamhead.moco.Moco.httpServer;
import static com.github.dreamhead.moco.Moco.text;
import static org.junit.Assert.assertEquals;

public class MocoJunitHttpUnitTest {

    private static final HttpServer server = httpServer(12306);

    static {
        server.response(text("JUnit 4 Response"));
    }

    @Rule
    public MocoJunitRunner runner = MocoJunitRunner.httpRunner(server);

    @Test
    public void givenMocoServer_whenClientSendsRequest_thenShouldReturnExpectedResponse() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:12306"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(response.body(), "JUnit 4 Response");
    }
}
