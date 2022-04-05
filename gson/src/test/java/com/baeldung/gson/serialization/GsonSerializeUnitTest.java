package com.baeldung.gson.serialization;

import com.baeldung.gson.entities.Movie;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.baeldung.gson.entities.ActorGson;
import com.baeldung.gson.entities.MovieWithNullValue;
import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

public class GsonSerializeUnitTest {

    @Test
    public void whenSimpleSerialize_thenCorrect() throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        ActorGson rudyYoungblood = new ActorGson("nm2199632", sdf.parse("21-09-1982"), Arrays.asList("Apocalypto", "Beatdown", "Wind Walkers"));
        Movie movie = new Movie("tt0472043", "Mel Gibson", Arrays.asList(rudyYoungblood));

        String expectedOutput = "{\"imdbId\":\"tt0472043\",\"director\":\"Mel Gibson\",\"actors\":[{\"imdbId\":\"nm2199632\",\"dateOfBirth\":\"Sep 21, 1982 12:00:00 AM\",\"filmography\":[\"Apocalypto\",\"Beatdown\",\"Wind Walkers\"]}]}";
        Assert.assertEquals(new Gson().toJson(movie), expectedOutput);
    }

    @Test
    public void whenCustomSerialize_thenCorrect() throws ParseException {
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().serializeNulls().disableHtmlEscaping().registerTypeAdapter(ActorGson.class, new ActorGsonSerializer()).create();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        ActorGson rudyYoungblood = new ActorGson("nm2199632", sdf.parse("21-09-1982"), Arrays.asList("Apocalypto", "Beatdown", "Wind Walkers"));
        MovieWithNullValue movieWithNullValue = new MovieWithNullValue(null, "Mel Gibson", Arrays.asList(rudyYoungblood));

        String expectedOutput = new GsonBuilder()
          .setPrettyPrinting()
          .serializeNulls()
          .disableHtmlEscaping()
          .create()
          .toJson(new JsonParser()
            .parse("{\"imdbId\":null,\"actors\":[{\"<strong>IMDB Code</strong>\":\"nm2199632\",\"<strong>Date Of Birth</strong>\":\"21-09-1982\",\"<strong>N° Film:</strong> \":3,\"filmography\":\"Apocalypto-Beatdown-Wind Walkers\"}]}"));
        Assert.assertEquals(gson.toJson(movieWithNullValue), expectedOutput);
    }
}
