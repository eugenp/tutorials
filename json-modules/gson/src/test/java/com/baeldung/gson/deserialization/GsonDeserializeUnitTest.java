package com.baeldung.gson.deserialization;

import java.text.ParseException;

import com.baeldung.gson.entities.Movie;
import com.baeldung.gson.serialization.ActorGsonDeserializer;
import com.baeldung.gson.entities.ActorGson;
import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonDeserializeUnitTest {
    @Test
    public void whenSimpleDeserialize_thenCorrect() throws ParseException {

        final String jsonInput = "{\"imdbId\":\"tt0472043\",\"actors\":" + "[{\"imdbId\":\"nm2199632\",\"dateOfBirth\":\"Tue Sep 21 11:00:00 GMT 1982\",\"filmography\":" + "[\"Apocalypto\",\"Beatdown\",\"Wind Walkers\"]}]}";

        final Gson gson = new GsonBuilder().setDateFormat("EEE MMM dd hh:mm:ss zzz yyyy").create();

        final Movie outputMovie = gson.fromJson(jsonInput, Movie.class);

        final String expectedOutput = "Movie [imdbId=tt0472043, director=null, actors=[ActorGson [imdbId=nm2199632, dateOfBirth=Tue Sep 21 11:00:00 GMT 1982, filmography=[Apocalypto, Beatdown, Wind Walkers]]]]";
        Assert.assertEquals(outputMovie.toString(), expectedOutput);
    }

    @Test
    public void whenCustomDeserialize_thenCorrect() throws ParseException {

        final String jsonInput = "{\"imdbId\":\"tt0472043\",\"actors\":" + "[{\"imdbId\":\"nm2199632\",\"dateOfBirth\":\"1982-09-21T12:00:00+01:00\",\"filmography\":" + "[\"Apocalypto\",\"Beatdown\",\"Wind Walkers\"]}]}";

        final Gson gson = new GsonBuilder().registerTypeAdapter(ActorGson.class, new ActorGsonDeserializer()).create();

        final Movie outputMovie = gson.fromJson(jsonInput, Movie.class);

        final String expectedOutput = "Movie [imdbId=tt0472043, director=null, actors=[ActorGson [imdbId=nm2199632, dateOfBirth=Tue Sep 21 11:00:00 GMT 1982, filmography=[Apocalypto, Beatdown, Wind Walkers]]]]";
        Assert.assertEquals(outputMovie.toString(), expectedOutput);
    }
}
