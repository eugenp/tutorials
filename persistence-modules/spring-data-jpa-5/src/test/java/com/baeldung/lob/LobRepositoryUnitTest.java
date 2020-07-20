package com.baeldung.lob;

import java.io.IOException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ContextConfiguration(classes = LobConfiguration.class)
class LobRepositoryUnitTest {

    @Autowired
    LobSampleRepository repository;

    @Test
    void givenLobFields_whenSave_shallInsertLobFields() throws IOException {
        LobSample entity = new LobSample();
        entity.setImage(ResourceReader.readResourceAsBytes("lob/wise-cat.jpeg"));
        entity.setBigText("My huge text file (...)".toCharArray());
        entity.setBigStringText("My huge String file (...)");

        Long catId = repository.save(entity)
            .getId();

        Assertions.assertThat(catId)
            .isEqualTo(1L);
    }

}
