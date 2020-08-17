package com.baeldung.lob;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ContextConfiguration(classes = { LobConfiguration.class, StorageContext.class, DatabaseStorage.class })
class DatabaseIntegrationTest {

    @Autowired
    StorageContext storageContext;
    @Autowired
    DatabaseRepository databaseRepository;

    @Test
    void givenFileNameAndContent_whenSave_shallInsertItToDatabase() {
        String fileUri = storageContext.save("file-name.jpg", "image.jpg".getBytes());

        assertThat(fileUri).startsWith("jdbc:h2://mem/");
        assertThat(databaseRepository.count()).isEqualTo(1L);
    }

}
