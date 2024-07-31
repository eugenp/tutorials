package com.baeldung.testcontainers.reuse;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

import com.baeldung.testcontainers.support.MiddleEarthCharacter;
import com.baeldung.testcontainers.support.MiddleEarthCharactersRepository;

@SpringBootTest
class ReusableContainersLiveTest {

    static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"))
      .withReuse(true);

    @BeforeAll
    static void beforeAll() {
        mongoDBContainer.start();
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    private MiddleEarthCharactersRepository repository;

    @Test
    void whenRunningMultipleTimes_thenContainerShouldBeReused_andTestShouldFail() {
        assertThat(repository.findAll())
          .isEmpty();

        repository.saveAll(List.of(
          new MiddleEarthCharacter("Frodo", "hobbit"),
          new MiddleEarthCharacter("Samwise", "hobbit"))
        );

        assertThat(repository.findAll())
          .hasSize(2);
    }

}
