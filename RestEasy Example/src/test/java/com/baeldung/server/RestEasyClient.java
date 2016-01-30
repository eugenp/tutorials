package com.baeldung.server;

import com.baeldung.model.Movie;
import com.baeldung.client.ServicesInterface;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.naming.NamingException;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class RestEasyClient {


    Movie  transformerMovie=null;
    Movie   batmanMovie=null;
    ObjectMapper jsonMapper=null;

    @BeforeClass
    public static void loadMovieInventory(){



    }

    @Before
    public void setup() throws ClassNotFoundException, IllegalAccessException, InstantiationException, NamingException {


        jsonMapper=new ObjectMapper().configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        jsonMapper.configure(DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
        jsonMapper.setDateFormat(sdf);

        try (InputStream inputStream = new RestEasyClient().getClass().getResourceAsStream("./movies/transformer.json")) {
            String transformerMovieAsString = String.format(IOUtils.toString(inputStream, StandardCharsets.UTF_8));
            transformerMovie = jsonMapper.readValue(transformerMovieAsString, Movie.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Test is going to die ...", e);
        }

        try (InputStream inputStream = new RestEasyClient().getClass().getResourceAsStream("./movies/batman.json")) {
            String batmanMovieAsString = String.format(IOUtils.toString(inputStream, StandardCharsets.UTF_8));
            batmanMovie = jsonMapper.readValue(batmanMovieAsString, Movie.class);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Test is going to die ...", e);
        }

    }

    @Test
    public void testListAllMovies() {

        try {
            ResteasyClient client = new ResteasyClientBuilder().build();
            ResteasyWebTarget target = client.target(UriBuilder.fromPath("http://localhost:8080/RestEasyTutorial/rest"));
            ServicesInterface simple = target.proxy(ServicesInterface.class);

            final List<Movie> movies = simple.listMovies();
            System.out.println(movies);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Test
    public void testAddMovie() {

        try {
            ResteasyClient client = new ResteasyClientBuilder().build();
            ResteasyWebTarget target = client.target(UriBuilder.fromPath("http://localhost:8080/RestEasyTutorial/rest"));

            ServicesInterface simple = target.proxy(ServicesInterface.class);
            final Response moviesResponse = simple.addMovie(batmanMovie);

            if (moviesResponse.getStatus() != 201) {
                System.out.println(moviesResponse.readEntity(String.class));
                throw new RuntimeException("Failed : HTTP error code : "
                        + moviesResponse.getStatus());
            }

            moviesResponse.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}