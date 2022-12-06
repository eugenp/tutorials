package com.baeldung.streamlargefile.client;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

public class LimitedFileDownloadWebClient {

    private LimitedFileDownloadWebClient() {
    }

    public static long fetch(WebClient client, String destination) throws IOException {
        Mono<byte[]> mono = client.get()
            .retrieve()
            .bodyToMono(byte[].class)
            .onErrorMap(RuntimeException::new);

        byte[] bytes = mono.block();

        Path path = Paths.get(destination);
        Files.write(path, bytes);

        return bytes.length;
    }

    public static void main(String... args) throws IOException {
        String baseUrl = args[0];
        String destination = args[1];

        WebClient client = WebClient.builder()
            .baseUrl(baseUrl)
            .exchangeStrategies(useMaxMemory())
            .build();

        long bytes = fetch(client, destination);
        System.out.printf("downloaded %d bytes to %s", bytes, destination);
    }

    public static ExchangeStrategies useMaxMemory() {
        long totalMemory = Runtime.getRuntime()
            .maxMemory();

        return ExchangeStrategies.builder()
            .codecs(configurer ->
                configurer.defaultCodecs()
                .maxInMemorySize((int) totalMemory))
            .build();
    }
}
