package com.baeldung.image;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("image")
public class ImageControllerLiveTest {

    @LocalServerPort
    private int port;

    private RestClient restClient;

    @BeforeEach
    void setup() {
        restClient = RestClient.builder()
                .baseUrl(String.format("http://localhost:%d", port))
                .build();
    }

    @Test
    void whenProvideColorAndImage_thenReturnStructuredOutput() throws Exception {
        // Prepare test data
        String colors = "blue,yellow,green";

        // Create a MultipartFile
        Path path = Paths.get("<replace-this-by-your-jpg-file>");
        byte[] imageBytes = Files.readAllBytes(path);
        MockMultipartFile file = new MockMultipartFile("file", "test-image.jpg", MediaType.IMAGE_JPEG_VALUE, imageBytes);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", file.getResource());
        body.add("colors", colors);

        CarCount carCount = restClient.post()
                .uri(uriBuilder -> uriBuilder.path("/image/car-count")
                        .queryParam("colors", colors)
                        .build())
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(body)
                .retrieve()
                .body(CarCount.class);

        assertTrue(carCount.getTotalCount() >= 0);
        assertTrue(carCount.getCarColorCounts().size() >= 0);
    }

}