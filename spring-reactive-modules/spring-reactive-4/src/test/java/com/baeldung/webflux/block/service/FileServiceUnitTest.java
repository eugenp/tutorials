package com.baeldung.webflux.block.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import reactor.test.StepVerifier;

@SpringJUnitConfig(classes = FileService.class)
class FileServiceUnitTest {
    @Value("${files.base.dir:/tmp/bael-7724}")
    private String filesBaseDir;

    @Autowired
    FileService fileService;

    @Test
    @DisplayName("Test can read the content of a file present under configured files base directory" +
        "Given" +
        "1. A file which is present on the classpath under files directory" +
        "When" +
        "1. Requested to get the content of a file by its name" +
        "Then" +
        "1. Return the Content of the file as a String")
    void givenAValidFile_wheRequestedToGetTheContentOfTheFile_thenReturnContentOfTheFileAsAString() {
        String fileName = "test.txt";
        String fileContent = "This is a test file.";

        setupATestFileWithContent(fileName, fileContent);

        StepVerifier
            .create(fileService.getFileContentAsString(fileName))
            .assertNext(actualFileContent -> assertEquals(fileContent, actualFileContent))
            .verifyComplete();
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
}