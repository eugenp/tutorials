package com.baeldung.server;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import javax.naming.NamingException;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.apache.commons.io.IOUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.client.jaxrs.engines.ApacheHttpClient43Engine;
import org.junit.Before;
import org.junit.Test;

import com.baeldung.client.ServicesInterface;
import com.baeldung.model.Movie;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RestEasyClientLiveTest {

    public static final UriBuilder FULL_PATH = UriBuilder.fromPath("http://127.0.0.1:8082/resteasy/rest");
    Movie transformerMovie = null;
    Movie batmanMovie = null;
    ObjectMapper jsonMapper = null;

    @Before
    public void setup() throws ClassNotFoundException, IllegalAccessException, InstantiationException, NamingException {

        jsonMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        jsonMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        final SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
        jsonMapper.setDateFormat(sdf);

        try (InputStream inputStream = new RestEasyClientLiveTest().getClass().getResourceAsStream("./movies/transformer.json")) {
            final String transformerMovieAsString = String.format(IOUtils.toString(inputStream, StandardCharsets.UTF_8));
            transformerMovie = jsonMapper.readValue(transformerMovieAsString, Movie.class);
        } catch (final Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Test is going to die ...", e);
        }

        try (InputStream inputStream = new RestEasyClientLiveTest().getClass().getResourceAsStream("./movies/batman.json")) {
            final String batmanMovieAsString = String.format(IOUtils.toString(inputStream, StandardCharsets.UTF_8));
            batmanMovie = jsonMapper.readValue(batmanMovieAsString, Movie.class);

        } catch (final Exception e) {
            throw new RuntimeException("Test is going to die ...", e);
        }
    }

    @Test
    public void testListAllMovies() {
        
        final ResteasyClient client = (ResteasyClient)ClientBuilder.newClient();
        final ResteasyWebTarget target = client.target(FULL_PATH);
        final ServicesInterface proxy = target.proxy(ServicesInterface.class);

        Response moviesResponse = proxy.addMovie(transformerMovie);
        moviesResponse.close();
        moviesResponse = proxy.addMovie(batmanMovie);
        moviesResponse.close();

        final List<Movie> movies = proxy.listMovies();
        System.out.println(movies);
    }

    @Test
    public void testMovieByImdbId() {

        final String transformerImdbId = "tt0418279";

        final ResteasyClient client = (ResteasyClient) ClientBuilder.newClient();
        final ResteasyWebTarget target = client.target(FULL_PATH);
        final ServicesInterface proxy = target.proxy(ServicesInterface.class);

        final Response moviesResponse = proxy.addMovie(transformerMovie);
        moviesResponse.close();

        final Movie movies = proxy.movieByImdbId(transformerImdbId);
        System.out.println(movies);
    }

    @Test
    public void testAddMovie() {

        final ResteasyClient client = (ResteasyClient) ClientBuilder.newClient();
        final ResteasyWebTarget target = client.target(FULL_PATH);
        final ServicesInterface proxy = target.proxy(ServicesInterface.class);

        Response moviesResponse = proxy.addMovie(batmanMovie);
        moviesResponse.close();
        moviesResponse = proxy.addMovie(transformerMovie);

        if (moviesResponse.getStatus() != Response.Status.CREATED.getStatusCode()) {
            System.out.println("Failed : HTTP error code : " + moviesResponse.getStatus());
        }

        moviesResponse.close();
        System.out.println("Response Code: " + moviesResponse.getStatus());
    }

    @Test
    public void testAddMovieMultiConnection() {

        final PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        final CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
        final ApacheHttpClient43Engine engine = new ApacheHttpClient43Engine(httpClient);
        final ResteasyClient client = ((ResteasyClientBuilder) ClientBuilder.newBuilder()).httpEngine(engine).build();
        final ResteasyWebTarget target = client.target(FULL_PATH);
        final ServicesInterface proxy = target.proxy(ServicesInterface.class);

        final Response batmanResponse = proxy.addMovie(batmanMovie);
        final Response transformerResponse = proxy.addMovie(transformerMovie);

        if (batmanResponse.getStatus() != Response.Status.CREATED.getStatusCode()) {
            System.out.println("Batman Movie creation Failed : HTTP error code : " + batmanResponse.getStatus());
        }
        if (batmanResponse.getStatus() != Response.Status.CREATED.getStatusCode()) {
            System.out.println("Batman Movie creation Failed : HTTP error code : " + batmanResponse.getStatus());
        }

        batmanResponse.close();
        transformerResponse.close();
        cm.close();

    }

    @Test
    public void testDeleteMovie() {

        final ResteasyClient client = (ResteasyClient) ClientBuilder.newClient();
        final ResteasyWebTarget target = client.target(FULL_PATH);
        final ServicesInterface proxy = target.proxy(ServicesInterface.class);

        Response moviesResponse = proxy.addMovie(batmanMovie);
        moviesResponse.close();
        moviesResponse = proxy.deleteMovie(batmanMovie.getImdbId());

        if (moviesResponse.getStatus() != Response.Status.OK.getStatusCode()) {
            System.out.println(moviesResponse.readEntity(String.class));
            throw new RuntimeException("Failed : HTTP error code : " + moviesResponse.getStatus());
        }

        moviesResponse.close();
        System.out.println("Response Code: " + moviesResponse.getStatus());
    }

    @Test
    public void testUpdateMovie() {

        final ResteasyClient client = (ResteasyClient) ClientBuilder.newClient();
        final ResteasyWebTarget target = client.target(FULL_PATH);
        final ServicesInterface proxy = target.proxy(ServicesInterface.class);

        Response moviesResponse = proxy.addMovie(batmanMovie);
        moviesResponse.close();
        batmanMovie.setTitle("Batman Begins");
        moviesResponse = proxy.updateMovie(batmanMovie);

        if (moviesResponse.getStatus() != Response.Status.OK.getStatusCode()) {
            System.out.println("Failed : HTTP error code : " + moviesResponse.getStatus());
        }

        moviesResponse.close();
        System.out.println("Response Code: " + moviesResponse.getStatus());
    }

}