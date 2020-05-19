package com.baeldung.spring.data.neo4j;

import com.baeldung.spring.data.neo4j.config.MovieDatabaseNeo4jTestConfiguration;
import com.baeldung.spring.data.neo4j.domain.Movie;
import com.baeldung.spring.data.neo4j.domain.Person;
import com.baeldung.spring.data.neo4j.domain.Role;
import com.baeldung.spring.data.neo4j.repository.MovieRepository;
import com.baeldung.spring.data.neo4j.repository.PersonRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MovieDatabaseNeo4jTestConfiguration.class)
@ActiveProfiles(profiles = "test")
public class MovieRepositoryIntegrationTest {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private PersonRepository personRepository;

    public MovieRepositoryIntegrationTest() {
    }

    @Before
    public void initializeDatabase() {
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

    @Test
    @DirtiesContext
    public void testFindByTitle() {
        System.out.println("findByTitle");
        String title = "The Italian Job";
        Movie result = movieRepository.findByTitle(title);
        assertNotNull(result);
        assertEquals(1999, result.getReleased());
    }

    @Test
    @DirtiesContext
    public void testCount() {
        System.out.println("count");
        long movieCount = movieRepository.count();
        assertNotNull(movieCount);
        assertEquals(1, movieCount);
    }

    @Test
    @DirtiesContext
    public void testFindAll() {
        System.out.println("findAll");
        Collection<Movie> result = (Collection<Movie>) movieRepository.findAll();
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    @DirtiesContext
    public void testFindByTitleContaining() {
        System.out.println("findByTitleContaining");
        String title = "Italian";
        Collection<Movie> result = movieRepository.findByTitleContaining(title);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    @DirtiesContext
    public void testGraph() {
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
    public void testDeleteMovie() {
        System.out.println("deleteMovie");
        movieRepository.delete(movieRepository.findByTitle("The Italian Job"));
        assertNull(movieRepository.findByTitle("The Italian Job"));
    }

    @Test
    @DirtiesContext
    public void testDeleteAll() {
        System.out.println("deleteAll");
        movieRepository.deleteAll();
        Collection<Movie> result = (Collection<Movie>) movieRepository.findAll();
        assertEquals(0, result.size());
    }
}
