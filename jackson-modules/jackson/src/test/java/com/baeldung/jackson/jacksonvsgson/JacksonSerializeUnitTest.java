package com.baeldung.jackson.jacksonvsgson;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class JacksonSerializeUnitTest {

    @Test
    public void whenSimpleSerialize_thenCorrect() throws JsonProcessingException, ParseException {

        final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        final ActorJackson rudyYoungblood = new ActorJackson("nm2199632", sdf.parse("21-09-1982"), Arrays.asList("Apocalypto", "Beatdown", "Wind Walkers"));
        final Movie movie = new Movie("tt0472043", "Mel Gibson", Arrays.asList(rudyYoungblood));

        final ObjectMapper mapper = new ObjectMapper();
        final String jsonResult = mapper.writeValueAsString(movie);

        final String expectedOutput = "{\"imdbId\":\"tt0472043\",\"director\":\"Mel Gibson\",\"actors\":[{\"imdbId\":\"nm2199632\",\"dateOfBirth\":401414400000,\"filmography\":[\"Apocalypto\",\"Beatdown\",\"Wind Walkers\"]}]}";
        Assert.assertEquals(jsonResult, expectedOutput);
    }

    @Test
    public void whenCustomSerialize_thenCorrect() throws ParseException, IOException {

        final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        final ActorJackson rudyYoungblood = new ActorJackson("nm2199632", sdf.parse("21-09-1982"), Arrays.asList("Apocalypto", "Beatdown", "Wind Walkers"));
        final MovieWithNullValue movieWithNullValue = new MovieWithNullValue(null, "Mel Gibson", Arrays.asList(rudyYoungblood));

        final SimpleModule module = new SimpleModule();
        module.addSerializer(new ActorJacksonSerializer(ActorJackson.class));
        final ObjectMapper mapper = new ObjectMapper();

        final String jsonResult = mapper.registerModule(module)
            .writer(new DefaultPrettyPrinter())
            .writeValueAsString(movieWithNullValue);

        final Object json = mapper.readValue("{\"actors\":[{\"imdbId\":\"nm2199632\",\"dateOfBirth\":\"21-09-1982\",\"N° Film: \":3,\"filmography\":\"Apocalypto-Beatdown-Wind Walkers\"}],\"imdbID\":null}", Object.class);
        final String expectedOutput = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT)
            .writeValueAsString(json);

        Assert.assertEquals(jsonResult, expectedOutput);
    }
}
