package org.baeldung.gson.deserialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.baeldung.gson.entities.ActorGson;
import org.baeldung.gson.entities.Movie;
import org.baeldung.gson.serialization.ActorGsonDeserializer;
import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;

public class GsonDeserializeTest {

    @Test
    public void whenSimpleDeserialize_thenCorrect() throws ParseException {

        String jsonInput = "{\"imdbId\":\"tt0472043\",\"actors\":" + "[{\"imdbId\":\"nm2199632\",\"dateOfBirth\":\"1982-09-21T12:00:00+01:00\",\"filmography\":" + "[\"Apocalypto\",\"Beatdown\",\"Wind Walkers\"]}]}";

        Movie outputMovie = new Gson().fromJson(jsonInput, Movie.class);

        String expectedOutput = "Movie [imdbId=tt0472043, director=null, actors=[ActorGson [imdbId=nm2199632, dateOfBirth=Tue Sep 21 04:00:00 PDT 1982, filmography=[Apocalypto, Beatdown, Wind Walkers]]]]";
        Assert.assertEquals(outputMovie.toString(), expectedOutput);
    }

    @Test
    public void whenCustomDeserialize_thenCorrect() throws ParseException {

        String jsonInput = "{\"imdbId\":\"tt0472043\",\"actors\":" + "[{\"imdbId\":\"nm2199632\",\"dateOfBirth\":\"1982-09-21T12:00:00+01:00\",\"filmography\":" + "[\"Apocalypto\",\"Beatdown\",\"Wind Walkers\"]}]}";

        Gson gson = new GsonBuilder().registerTypeAdapter(ActorGson.class, new ActorGsonDeserializer()).create();

        Movie outputMovie = gson.fromJson(jsonInput, Movie.class);

        String expectedOutput = "Movie [imdbId=tt0472043, director=null, actors=[ActorGson [imdbId=nm2199632, dateOfBirth=Tue Sep 21 12:00:00 PDT 1982, filmography=[Apocalypto, Beatdown, Wind Walkers]]]]";
        Assert.assertEquals(outputMovie.toString(), expectedOutput);
    }
}
