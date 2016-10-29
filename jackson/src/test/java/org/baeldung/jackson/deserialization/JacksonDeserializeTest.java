package org.baeldung.jackson.deserialization;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.baeldung.jackson.entities.Movie;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonDeserializeTest {

    @Test
    public void whenSimpleDeserialize_thenCorrect() throws IOException {

        final String jsonInput = "{\"imdbId\":\"tt0472043\",\"actors\":[{\"imdbId\":\"nm2199632\",\"dateOfBirth\":\"1982-09-21T12:00:00+01:00\",\"filmography\":[\"Apocalypto\",\"Beatdown\",\"Wind Walkers\"]}]}";
        final ObjectMapper mapper = new ObjectMapper();
        final Movie movie = mapper.readValue(jsonInput, Movie.class);

        final String expectedOutput = "Movie [imdbId=tt0472043, director=null, actors=[ActorJackson [imdbId=nm2199632, dateOfBirth=Tue Sep 21 11:00:00 GMT 1982, filmography=[Apocalypto, Beatdown, Wind Walkers]]]]";
        Assert.assertEquals(movie.toString(), expectedOutput);
    }

    @Test
    public void whenCustomDeserialize_thenCorrect() throws IOException {

        final String jsonInput = "{\"imdbId\":\"tt0472043\",\"director\":\"Mel Gibson\",\"actors\":[{\"imdbId\":\"nm2199632\",\"dateOfBirth\":\"1982-09-21T12:00:00+01:00\",\"filmography\":[\"Apocalypto\",\"Beatdown\",\"Wind Walkers\"]}]}";

        final ObjectMapper mapper = new ObjectMapper();
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        mapper.setDateFormat(df);

        final Movie movie = mapper.readValue(jsonInput, Movie.class);

        final String expectedOutput = "Movie [imdbId=tt0472043, director=Mel Gibson, actors=[ActorJackson [imdbId=nm2199632, dateOfBirth=Tue Sep 21 11:00:00 GMT 1982, filmography=[Apocalypto, Beatdown, Wind Walkers]]]]";
        Assert.assertEquals(movie.toString(), expectedOutput);
    }

}
