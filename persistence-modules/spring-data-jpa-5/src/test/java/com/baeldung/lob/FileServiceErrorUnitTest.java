package com.baeldung.lob;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootApplication
class FileServiceErrorUnitTest {

    static final Path NEW_DIRECTORY = ResourceReader.resourcePath();
    static String EXISTING_FILE = "/lob/wise-cat.jpeg";

    @Autowired
    FileService fileService;

    @Mock
    FileLocationRepository fileLocationRepository;

    @BeforeEach
    void clean() throws IOException {
        Files.deleteIfExists(Paths.get(NEW_DIRECTORY + "/wise-cat.jpeg"));
    }

    @Test
    void givenErrorSavingLocation_whenSave_shallExistNothingNeitherInTheDatabaseNorFileSystem() throws IOException {
        fileService.save(ResourceReader.readResourceAsFile(EXISTING_FILE), NEW_DIRECTORY);

        when(fileLocationRepository.save(any()))
            .thenThrow(new RuntimeException());

        Path actualFileInSystem = Paths.get(NEW_DIRECTORY + "/wise-cat.jpeg");
        assertThat(Files.exists(actualFileInSystem))
            .isFalse();
    }
}
