package com.baeldung.lob;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootApplication
class FileServiceUnitTest {

    static final Path NEW_DIRECTORY = ResourceReader.resourcePath();
    static String EXISTING_FILE = "/lob/wise-cat.jpeg";

    @Autowired
    FileService fileService;

    @Autowired
    FileLocationRepository fileLocationRepository;

    @Test
    void givenFile_whenSave_shallSaveItToFileSystemAndItsLocationInTheDatabase() throws IOException {
        fileService.save(ResourceReader.readResourceAsFile(EXISTING_FILE), NEW_DIRECTORY);

        String fileLocation = fileLocationRepository.findById(1L)
            .get()
            .getFilePath();

        Path actualFileInSystem = Paths.get(fileLocation);

        assertThat(Files.exists(actualFileInSystem))
            .isTrue();
    }

}
