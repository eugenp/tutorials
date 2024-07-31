package com.baeldung.testcontainers.support;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.hasItems;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.baeldung.testcontainers.support.MiddleEarthCharacter;
import com.baeldung.testcontainers.support.MiddleEarthCharactersRepository;

@Testcontainers
@SpringBootTest(webEnvironment = DEFINED_PORT)
@DirtiesContext(classMode = AFTER_CLASS)
// Testcontainers require a valid docker installation.
// When running the tests, ensure you have a valid Docker environment
class DynamicPropertiesLiveTest {
    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    private MiddleEarthCharactersRepository repository;

    @BeforeEach
    void beforeEach() {
        repository.deleteAll();
    }

    @Test
    void whenRequestingHobbits_thenReturnFrodoAndSam() {
        repository.saveAll(List.of(
            new MiddleEarthCharacter("Frodo", "hobbit"),
            new MiddleEarthCharacter("Samwise", "hobbit"),
            new MiddleEarthCharacter("Aragon", "human"),
            new MiddleEarthCharacter("Gandalf", "wizard")
        ));

        when().get("/characters?race=hobbit")
          .then().statusCode(200)
          .and().body("name", hasItems("Frodo", "Samwise"));
    }

}
