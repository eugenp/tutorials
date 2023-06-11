package com.baeldung.streamlargefile.client;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;

public class LargeFileDownloadWebClient {

    private LargeFileDownloadWebClient() {
    }

    public static long fetch(WebClient client, String destination) throws IOException {
        Flux<DataBuffer> flux = client.get()
            .retrieve()
            .bodyToFlux(DataBuffer.class);

        Path path = Paths.get(destination);

        DataBufferUtils.write(flux, path)
            .block();

        return Files.size(path);
    }

    public static void main(String... args) throws IOException {
        String baseUrl = args[0];
        String destination = args[1];

        WebClient client = WebClient.create(baseUrl);

        long bytes = fetch(client, destination);
        System.out.printf("downloaded %d bytes to %s", bytes, destination);
    }
}
