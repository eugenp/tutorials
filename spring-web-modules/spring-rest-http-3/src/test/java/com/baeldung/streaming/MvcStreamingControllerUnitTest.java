package com.baeldung.streaming;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MvcStreamingControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    void cleanupTestFiles() throws IOException {
        Path uploadDir = Path.of("mvc-uploads");

        if (Files.exists(uploadDir)) {
            // Delete the entire directory and its contents
            Files.walk(uploadDir)
              .sorted(Comparator.reverseOrder()) // delete files first, then directory
              .forEach(path -> {
                  try {
                      Files.deleteIfExists(path);
                  } catch (IOException e) {
                      // Log the error but don't fail the test
                      System.err.println("Failed to delete file: " + path + ", Error: " + e.getMessage());
                  }
              });
        }
    }

    @Test
    void givenMultipartFile_whenUploadEndpointCalled_thenFileIsSavedAndSuccessResponseReturned() throws Exception {
        // Given
        String testContent = "Hello, World!";
        MockMultipartFile file = new MockMultipartFile(
          "filePart",
          "test-file.txt",
          "text/plain",
          testContent.getBytes()
        );

        // When
        mockMvc.perform(multipart("/mvc/files/upload").file(file))
          // Then
          .andExpect(status().isOk())
          .andExpect(content().string("Upload successful: test-file.txt"));

        // Then - Verify file was actually created
        Path uploadedFilePath = Path.of("mvc-uploads/test-file.txt");
        assertTrue(Files.exists(uploadedFilePath), "Uploaded file should exist on the filesystem");
        assertTrue(Files.readString(uploadedFilePath).equals(testContent), "Uploaded file content should match the original");
    }

    @Test
    void givenSourceFilesExistInUploadDirectory_whenDownloadEndpointCalled_thenMultipartResponseWithCorrectContentTypeReturned() throws Exception {
        // Given - Create the source files that the controller expects to find
        Files.createDirectories(Path.of("mvc-uploads"));
        Files.writeString(Path.of("mvc-uploads/file1.txt"), "content of file1");
        Files.writeString(Path.of("mvc-uploads/file2.txt"), "content of file2");

        // When & Then
        mockMvc.perform(get("/mvc/files/download"))
          .andExpect(status().isOk())
          .andExpect(header().string("Content-Type", "multipart/mixed; boundary=filesBoundary"));
    }
}