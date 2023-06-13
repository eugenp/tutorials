package com.baeldung.spring.data.neo4j;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.baeldung.spring.data.neo4j.config.MovieDatabaseNeo4jTestConfiguration;
import com.baeldung.spring.data.neo4j.domain.Movie;
import com.baeldung.spring.data.neo4j.domain.Person;
import com.baeldung.spring.data.neo4j.domain.Role;
import com.baeldung.spring.data.neo4j.repository.MovieRepository;
import com.baeldung.spring.data.neo4j.repository.PersonRepository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.neo4j.harness.Neo4j;
import org.neo4j.harness.Neo4jBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.util.*;

@ContextConfiguration(classes = MovieDatabaseNeo4jTestConfiguration.class)
@ActiveProfiles(profiles = "test")
@DataNeo4jTest
class MovieRepositoryIntegrationTest {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private PersonRepository personRepository;

    public MovieRepositoryIntegrationTest() {
    }

    private static Neo4j embeddedDatabaseServer;

    @BeforeAll
    static void setup() {
        embeddedDatabaseServer = Neo4jBuilders.newInProcessBuilder()
                .withDisabledServer()
                .build();
    }

    @BeforeEach
    void initializeNeo4j() {

        System.out.println("seeding embedded database");
        Movie italianJob = new Movie();
        italianJob.setTitle("The Italian Job");
        italianJob.setReleased(1999);
        movieRepository.save(italianJob);

        Person mark = new Person();
        mark.setName("Mark Wahlberg");
        personRepository.save(mark);

        Role charlie = new Role();
        charlie.setMovie(italianJob);
        charlie.setPerson(mark);
        Collection<String> roleNames = new HashSet<>();
        roleNames.add("Charlie Croker");
        charlie.setRoles(roleNames);
        List<Role> roles = new ArrayList<>();
        roles.add(charlie);
        italianJob.setRoles(roles);
        movieRepository.save(italianJob);
    }

    @DynamicPropertySource
    static void neo4jProperties(DynamicPropertyRegistry registry) {

        registry.add("spring.neo4j.uri", embeddedDatabaseServer::boltURI);
        registry.add("spring.neo4j.authentication.username", () -> "neo4j");
        registry.add("spring.neo4j.authentication.password", () -> null);
    }

    @AfterAll
    static void stopNeo4j() {

        embeddedDatabaseServer.close();
    }

    @Test
    @DirtiesContext
    void testFindByTitle() {
        System.out.println("findByTitle");
        String title = "The Italian Job";
        Movie result = movieRepository.findByTitle(title);
        assertNotNull(result);
        assertEquals(1999, result.getReleased());
    }

    @Test
    @DirtiesContext
    void testCount() {
        System.out.println("count");
        long movieCount = movieRepository.count();

        assertEquals(1, movieCount);
    }

    @Test
    @DirtiesContext
    void testFindAll() {
        System.out.println("findAll");
        Collection<Movie> result = movieRepository.findAll();
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    @DirtiesContext
    void testFindByTitleContaining() {
        System.out.println("findByTitleContaining");
        String title = "Italian";
        Collection<Movie> result = movieRepository.findByTitleContaining(title);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    @DirtiesContext
    void testGraph() {
        System.out.println("graph");
        List<Map<String, Object>> graph = movieRepository.graph(5);
        assertEquals(1, graph.size());
        Map<String, Object> map = graph.get(0);
        assertEquals(2, map.size());
        String[] cast = (String[]) map.get("cast");
        String movie = (String) map.get("movie");
        assertEquals("The Italian Job", movie);
        assertEquals("Mark Wahlberg", cast[0]);
    }

    @Test
    @DirtiesContext
    void testDeleteMovie() {
        System.out.println("deleteMovie");
        movieRepository.delete(movieRepository.findByTitle("The Italian Job"));
        assertNull(movieRepository.findByTitle("The Italian Job"));
    }

    @Test
    @DirtiesContext
    void testDeleteAll() {
        System.out.println("deleteAll");
        movieRepository.deleteAll();
        Collection<Movie> result = movieRepository.findAll();
        assertEquals(0, result.size());
    }
}
