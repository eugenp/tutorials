package com.baeldung.databuffer;

import com.baeldung.databuffer.DataBufferToInputStream;

import io.restassured.internal.util.IOUtils;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DataBufferToInputStreamUnitTest {
    private String getResponseStub() throws IOException {
        InputStream inputStream = null;
        BufferedReader reader = null;
        String content = null;
        try {
            inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("user-response.json");
            if (inputStream != null) {
                reader = new BufferedReader(new InputStreamReader(inputStream));
                content = reader.lines()
                    .collect(Collectors.joining(System.lineSeparator()));
            }
        } catch (Exception ex) {
            throw new RuntimeException("exception caught while getting response stub");
        } finally {
            reader.close();
            inputStream.close();
        }
        return content;
    }

    private InputStream getResponseStubAsInputStream() {
        return this.getClass()
            .getClassLoader()
            .getResourceAsStream("user-response.json");
    }

    private WebClient getMockWebClient() throws IOException {
        String content = getResponseStub();
        ClientResponse clientResponse = ClientResponse.create(HttpStatus.OK)
            .header("Content-Type", "application/json")
            .body(content)
            .build();

        ExchangeFunction exchangeFunction = clientRequest -> Mono.just(clientResponse);

        WebClient.Builder webClientBuilder = WebClient.builder()
            .exchangeFunction(exchangeFunction);
        WebClient webClient = webClientBuilder.build();
        return webClient;
    }

    @Test
    public void testResponseAsInputStream() throws IOException, InterruptedException {
        String mockUrl = "http://mockurl.com";
        WebClient mockWebClient = getMockWebClient();
        InputStream inputStream = DataBufferToInputStream.getResponseAsInputStream(mockWebClient, mockUrl);
        byte[] expectedBytes = IOUtils.toByteArray(getResponseStubAsInputStream());
        byte[] actualBytes = IOUtils.toByteArray(inputStream);
        assertArrayEquals(expectedBytes, actualBytes);
    }
}