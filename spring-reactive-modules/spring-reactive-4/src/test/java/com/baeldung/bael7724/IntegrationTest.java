package com.baeldung.bael7724;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class IntegrationTest {
    private static final String FILES_GET_ENDPOINT = "/bael7724/v1/files/{id}";
    private static final String BLOCKING_FILE_SEARCH_ENDPOINT = "/bael7724/v1/files/{id}/blocking-search";
    private static final String NON_BLOCKING_FILE_SEARCH_ENDPOINT = "/bael7724/v1/files/{id}/blocking-search";

    @Value("${files.base.dir:/tmp/bael-7724}")
    private String filesBaseDir;

    @Autowired
    WebTestClient webTestClient;

    @Test
    @DisplayName("Test a file is served" +
        "Given " +
        "1. A valid file name" +
        "2. It's available on the server" +
        "When " +
        "1. Files endpoint is requested with the valid file name" +
        "Then " +
        "1. Return the file data")
    void testAFileIsServed() {
        // Given
        String validFileName = "robots.txt";
        Optional<String> expectedFileContent = getBytesFromAResourceFile(validFileName);

        if(!expectedFileContent.isPresent()) {
            fail("Unable to load expected test file content. FileName=" + validFileName);
        }

        setupATestFileWithContent(validFileName, expectedFileContent.get());

        // When
        webTestClient.get()
            .uri(uriBuilder -> uriBuilder.path(FILES_GET_ENDPOINT)
                .build(validFileName))
            .exchange()
            .expectStatus()
            .is2xxSuccessful()
            .expectBody(String.class)
            .isEqualTo(expectedFileContent.get());
    }

    private Optional<String> getBytesFromAResourceFile(String validFileName) {
        try {
            URL testFilePathURL = this.getClass().getClassLoader().getResource(validFileName);
            assert testFilePathURL != null;
            Path testFilePath = Paths.get(testFilePathURL.toURI());
            return Optional.of(new String(Files.readAllBytes(testFilePath), StandardCharsets.UTF_8));
        } catch (IOException | URISyntaxException e){
            return Optional.empty();
        }
    }

    private void setupATestFileWithContent(String fileName, String fileContent) {
        if(!Paths.get(filesBaseDir).toFile().exists()) {
            try {
                Files.createDirectories(Paths.get(filesBaseDir));
            } catch (IOException e) {
                throw new RuntimeException("Unable to create files base dir, path=" + filesBaseDir, e);
            }
        }

        try {
            Files.write(Paths.get(filesBaseDir + "/" + fileName), (fileContent).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException("Unable to setup Test file.", e);
        }
    }

    @Test
    @DisplayName("Test search the file using the blocking API leads to Server Error" +
        "GIVEN" +
        "   1. A valid file name" +
        "   2. The file exits in the configured files.base.dir" +
        "WHEN" +
        "   1. Such a file is requested by its name" +
        "THEN" +
        "   1. Expect a 500 response form the server")
    void testReadingTheFileUsingTheBlockingAPILeadsToServerError() {
        // Given
        String validFileName = "robots.txt";
        Optional<String> expectedFileContent = getBytesFromAResourceFile(validFileName);

        if (!expectedFileContent.isPresent()) {
            fail("Unable to load expected test file content. FileName=" + validFileName);
        }


        String searchTerm = expectedFileContent.get().substring(3, 5);

        setupATestFileWithContent(validFileName, expectedFileContent.get());

        // When
        webTestClient.get()
            .uri(uriBuilder -> uriBuilder.path(BLOCKING_FILE_SEARCH_ENDPOINT)
                .queryParam("term", searchTerm)
                .build(validFileName))
            .exchange()
            .expectStatus()
            .is5xxServerError();
    }
}
