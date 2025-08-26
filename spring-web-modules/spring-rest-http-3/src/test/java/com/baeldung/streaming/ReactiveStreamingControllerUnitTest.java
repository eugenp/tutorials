package com.baeldung.streaming;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

// To test the reactive endpoints, uncomment spring.main.web-application-type=reactive in the application properties,
// which switches the application from default MVC mode to reactive mode.
@SpringBootTest
@AutoConfigureWebTestClient
class ReactiveStreamingControllerUnitTest {
    /*
    @Autowired
    private WebTestClient webTestClient;

    private static final Path UPLOAD_DIR = Path.of("reactive-uploads");

    @BeforeEach
    void setUp() throws Exception {
        Files.createDirectories(UPLOAD_DIR);
        Files.writeString(UPLOAD_DIR.resolve("file1.txt"), "Hello from file1");
        Files.writeString(UPLOAD_DIR.resolve("file2.txt"), "Hello from file2");
    }

    @Test
    void givenFilePart_whenUpload_thenSuccessMessage() {
        byte[] content = "Reactive upload content".getBytes();
        ByteArrayResource resource = new ByteArrayResource(content) {
            @Override
            public String getFilename() {
                return "upload.txt";
            }
        };

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("filePart", resource);

        webTestClient.post()
          .uri("/reactive/files/upload")
          .contentType(MediaType.MULTIPART_FORM_DATA)
          .bodyValue(body)
          .exchange()
          .expectStatus().isOk()
          .expectBody(String.class)
          .value(bodyStr -> assertThat(bodyStr).contains("Upload successful: upload.txt"));

        assertThat(Files.exists(UPLOAD_DIR.resolve("upload.txt"))).isTrue();
    }

    @Test
    void givenExistingFiles_whenDownload_thenMultipartResponseContainsFiles() {
        webTestClient.get()
          .uri("/reactive/files/download")
          .exchange()
          .expectStatus().isOk()
          .expectHeader().contentTypeCompatibleWith("multipart/mixed")
          .expectBody(String.class)
          .value(body -> {
              assertThat(body).contains("Hello from file1");
              assertThat(body).contains("Hello from file2");
              assertThat(body).contains("Content-Disposition: attachment; filename=\"file1.txt\"");
              assertThat(body).contains("Content-Disposition: attachment; filename=\"file2.txt\"");
          });
    }
     */
}
