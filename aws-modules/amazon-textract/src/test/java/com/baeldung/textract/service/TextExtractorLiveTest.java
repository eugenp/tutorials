package com.baeldung.textract.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintViolationException;
import net.bytebuddy.utility.RandomString;

@SpringBootTest
class TextExtractorLiveTest {

    @Autowired
    private TextExtractor textExtractor;

    @Test
    void whenTextractCalledWithImage_thenCorrectTextExtracted() throws IOException {
        String fileName = "sample-image.png";
        Path filePath = new ClassPathResource(fileName).getFile().toPath();
        ByteArrayInputStream fileContent = new ByteArrayInputStream(Files.readAllBytes(filePath));
        MultipartFile file = new MockMultipartFile(fileName, fileName, "image/png", fileContent);

        String response = textExtractor.extract(file);

        assertThat(response).isEqualTo("Exploring Amazon Textract");
    }

    @Test
    void whenTextractCalledWithS3Object_thenCorrectTextExtracted() {
        String bucketName = "baeldung-amazon-textract-tutorial-bucket";
        String objectKey = "sample-image.png";

        String response = textExtractor.extract(bucketName, objectKey);

        assertThat(response).isEqualTo("Exploring Amazon Textract");
    }

    @Test
    void whenInvalidFileTypeProvided_thenConstraintViolationExceptionThrown() throws IOException {
        String fileName = "invalid-file.txt";
        ByteArrayInputStream fileContent = new ByteArrayInputStream(RandomString.make().getBytes());
        MultipartFile invalidFile = new MockMultipartFile(fileName, fileName, "text/plain", fileContent);

        assertThatThrownBy(() -> textExtractor.extract(invalidFile))
            .isInstanceOf(ConstraintViolationException.class)
            .hasMessageContaining("Invalid file type. Allowed types are PNG, JPEG, TIFF, and PDF.");
    }

}