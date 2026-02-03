package com.baeldung.contentlenght;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ContentLengthControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    private static final Path TEMP_DIR = Path.of("temp-test-files");

    @AfterEach
    void cleanupTestFiles() throws IOException {
        if (Files.exists(TEMP_DIR)) {
            Files.walk(TEMP_DIR)
              .sorted(Comparator.reverseOrder())
              .forEach(path -> {
                  try {
                      Files.deleteIfExists(path);
                  } catch (IOException e) {
                      System.err.println("Failed to delete file: " + path + ", Error: " + e.getMessage());
                  }
              });
        }
    }

    @Test
    void givenHelloEndpoint_whenCalled_thenContentLengthMatchesUtf8Bytes() throws Exception {
        String expectedBody = "Hello Spring MVC!";
        byte[] expectedBytes = expectedBody.getBytes(StandardCharsets.UTF_8);

        mockMvc.perform(get("/hello"))
          .andExpect(status().isOk())
          .andExpect(header().longValue("Content-Length", expectedBytes.length))
          .andExpect(content().string(expectedBody));
    }

    @Test
    void givenBinaryEndpoint_whenCalled_thenContentLengthMatchesByteArray() throws Exception {
        byte[] expectedData = {1, 2, 3, 4, 5};

        mockMvc.perform(get("/binary"))
          .andExpect(status().isOk())
          .andExpect(header().longValue("Content-Length", expectedData.length))
          .andExpect(header().string("Content-Type", MediaType.APPLICATION_OCTET_STREAM_VALUE))
          .andExpect(content().bytes(expectedData));
    }

    @Test
    void givenDownloadEndpoint_whenFileExists_thenContentLengthMatchesFileSize() throws Exception {
        Files.createDirectories(TEMP_DIR);
        Path tempFile = TEMP_DIR.resolve("example.pdf");
        byte[] fileContent = "Test PDF content".getBytes(StandardCharsets.UTF_8);
        Files.write(tempFile, fileContent);
        long expectedSize = Files.size(tempFile);

        Path controllerFilePath = Paths.get("example.pdf");
        Files.copy(tempFile, controllerFilePath);

        try {
            mockMvc.perform(get("/download"))
              .andExpect(status().isOk())
              .andExpect(header().longValue("Content-Length", expectedSize))
              .andExpect(header().string("Content-Disposition", "attachment; filename=\"example.pdf\""))
              .andExpect(header().string("Content-Type", "application/pdf"));
        } finally {
            // Cleanup project root copy
            Files.deleteIfExists(controllerFilePath);
        }
    }

    @Test
    void givenManualEndpoint_whenCalled_thenContentLengthMatchesUtf8Bytes() throws Exception {
        String expectedBody = "Manual Content-Length";
        byte[] expectedBytes = expectedBody.getBytes(StandardCharsets.UTF_8);

        mockMvc.perform(get("/manual"))
          .andExpect(status().isOk())
          .andExpect(header().longValue("Content-Length", expectedBytes.length))
          .andExpect(content().string(expectedBody));
    }
}

