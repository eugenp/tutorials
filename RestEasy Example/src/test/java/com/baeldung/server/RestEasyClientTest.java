package com.baeldung.server;

import com.baeldung.client.ServicesInterface;
import com.baeldung.model.Movie;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.junit.Before;
import org.junit.Test;
import javax.naming.NamingException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class RestEasyClientTest {

    Movie transformerMovie = null;
    Movie batmanMovie = null;
    ObjectMapper jsonMapper = null;

    @Before
    public void setup() throws ClassNotFoundException, IllegalAccessException, InstantiationException, NamingException {

        jsonMapper = new ObjectMapper().configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        jsonMapper.configure(DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
        jsonMapper.setDateFormat(sdf);

        try (InputStream inputStream = new RestEasyClientTest().getClass().getResourceAsStream("./movies/transformer.json")) {
            String transformerMovieAsString = String.format(IOUtils.toString(inputStream, StandardCharsets.UTF_8));
            transformerMovie = jsonMapper.readValue(transformerMovieAsString, Movie.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Test is going to die ...", e);
        }

        try (InputStream inputStream = new RestEasyClientTest().getClass().getResourceAsStream("./movies/batman.json")) {
            String batmanMovieAsString = String.format(IOUtils.toString(inputStream, StandardCharsets.UTF_8));
            batmanMovie = jsonMapper.readValue(batmanMovieAsString, Movie.class);

        } catch (Exception e) {
            throw new RuntimeException("Test is going to die ...", e);
        }
    }

    @Test
    public void testListAllMovies() {

        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(UriBuilder.fromPath("http://127.0.0.1:8080/RestEasyTutorial/rest"));
        ServicesInterface simple = target.proxy(ServicesInterface.class);

        Response moviesResponse = simple.addMovie(transformerMovie);
        moviesResponse.close();
        moviesResponse = simple.addMovie(batmanMovie);
        moviesResponse.close();

        List<Movie> movies = simple.listMovies();
        System.out.println(movies);
    }

    @Test
    public void testMovieByImdbId() {

        String transformerImdbId = "tt0418279";

        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(UriBuilder.fromPath("http://127.0.0.1:8080/RestEasyTutorial/rest"));
        ServicesInterface simple = target.proxy(ServicesInterface.class);

        Response moviesResponse = simple.addMovie(transformerMovie);
        moviesResponse.close();

        Movie movies = simple.movieByImdbId(transformerImdbId);
        System.out.println(movies);
    }

    @Test
    public void testAddMovie() {

        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(UriBuilder.fromPath("http://127.0.0.1:8080/RestEasyTutorial/rest"));
        ServicesInterface simple = target.proxy(ServicesInterface.class);

        Response moviesResponse = simple.addMovie(batmanMovie);
        moviesResponse.close();
        moviesResponse = simple.addMovie(transformerMovie);

        if (moviesResponse.getStatus() != Response.Status.CREATED.getStatusCode()) {
            System.out.println("Failed : HTTP error code : " + moviesResponse.getStatus());
        }

        moviesResponse.close();
        System.out.println("Response Code: " + Response.Status.OK.getStatusCode());
    }

    @Test
    public void testDeleteMovi1e() {

        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(UriBuilder.fromPath("http://127.0.0.1:8080/RestEasyTutorial/rest"));
        ServicesInterface simple = target.proxy(ServicesInterface.class);

        Response moviesResponse = simple.addMovie(batmanMovie);
        moviesResponse.close();
        moviesResponse = simple.deleteMovie(batmanMovie.getImdbId());

        if (moviesResponse.getStatus() != Response.Status.OK.getStatusCode()) {
            System.out.println(moviesResponse.readEntity(String.class));
            throw new RuntimeException("Failed : HTTP error code : " + moviesResponse.getStatus());
        }

        moviesResponse.close();
        System.out.println("Response Code: " + Response.Status.OK.getStatusCode());
    }

    @Test
    public void testUpdateMovie() {

        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(UriBuilder.fromPath("http://127.0.0.1:8080/RestEasyTutorial/rest"));
        ServicesInterface simple = target.proxy(ServicesInterface.class);

        Response moviesResponse = simple.addMovie(batmanMovie);
        moviesResponse.close();
        batmanMovie.setImdbVotes("300,000");
        moviesResponse = simple.updateMovie(batmanMovie);

        if (moviesResponse.getStatus() != Response.Status.OK.getStatusCode()) {
            System.out.println("Failed : HTTP error code : " + moviesResponse.getStatus());
        }

        moviesResponse.close();
        System.out.println("Response Code: " + Response.Status.OK.getStatusCode());
    }

}