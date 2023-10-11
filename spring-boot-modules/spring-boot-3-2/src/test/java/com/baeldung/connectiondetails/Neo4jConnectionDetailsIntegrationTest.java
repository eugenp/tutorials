package com.baeldung.connectiondetails;

import com.baeldung.connectiondetails.configuration.CustomNeo4jConnectionDetailsConfiguration;
import com.baeldung.connectiondetails.entity.neo4j.Person;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConnectionDetailsApplication.class)
@Import(CustomNeo4jConnectionDetailsConfiguration.class)
@ComponentScan(basePackages = "com.baeldung.connectiondetails")
@TestPropertySource(locations = {"classpath:connectiondetails/application-neo4j.properties"})
@ActiveProfiles("neo4j")
public class Neo4jConnectionDetailsIntegrationTest {
    @Autowired
    private Neo4jTemplate neo4jTemplate;

    @After
    public void cleanup() {
        neo4jTemplate.deleteAll(Person.class);
    }
    @Test
    public void giveSecretVault_whenRunQuery_thenSuccess() {
        Person person = new Person();
        person.setName("James");
        person.setZipcode("751003");

        Person data = neo4jTemplate.save(person);
        assertEquals("James", data.getName());
    }

}
