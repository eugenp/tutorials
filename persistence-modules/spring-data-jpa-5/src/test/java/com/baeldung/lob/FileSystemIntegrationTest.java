package com.baeldung.lob;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

@DataJpaTest
@ContextConfiguration(classes = { LobConfiguration.class, StorageContext.class, FileSystemStorage.class })
class FileSystemIntegrationTest {

    @Autowired
    StorageContext storageContext;

    @Test
    void givenFileNameAndContent_whenSave_shallInsertItToFileSystem() throws URISyntaxException {
        String fileUri = storageContext.save("file-name.jpg", "image.jpg".getBytes());

        assertThat(fileUri).startsWith("file:///");
        Files.exists(Paths.get(new URI(fileUri)));
    }
}
