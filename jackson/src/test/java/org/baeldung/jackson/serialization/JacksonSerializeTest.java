package org.baeldung.jackson.serialization;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.baeldung.jackson.entities.ActorJackson;
import org.baeldung.jackson.entities.Movie;
import org.baeldung.jackson.entities.MovieWithNullValue;
import org.baeldung.jackson.serialization.ActorJacksonSerializer;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class JacksonSerializeTest {

    @Test
    public void whenSimpleSerialize_thenCorrect() throws JsonProcessingException, ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        ActorJackson rudyYoungblood = new ActorJackson("nm2199632", sdf.parse("21-09-1982"), Arrays.asList("Apocalypto", "Beatdown", "Wind Walkers"));
        Movie movie = new Movie("tt0472043", "Mel Gibson", Arrays.asList(rudyYoungblood));

        ObjectMapper mapper = new ObjectMapper();
        String jsonResult = mapper.writeValueAsString(movie);

        String expectedOutput = "{\"imdbId\":\"tt0472043\",\"director\":\"Mel Gibson\",\"actors\":[{\"imdbId\":\"nm2199632\",\"dateOfBirth\":401439600000,\"filmography\":[\"Apocalypto\",\"Beatdown\",\"Wind Walkers\"]}]}";
        Assert.assertEquals(jsonResult, expectedOutput);
    }

    @Test
    public void whenCustomSerialize_thenCorrect() throws ParseException, IOException {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        ActorJackson rudyYoungblood = new ActorJackson("nm2199632", sdf.parse("21-09-1982"), Arrays.asList("Apocalypto", "Beatdown", "Wind Walkers"));
        MovieWithNullValue movieWithNullValue = new MovieWithNullValue(null, "Mel Gibson", Arrays.asList(rudyYoungblood));

        SimpleModule module = new SimpleModule();
        module.addSerializer(new ActorJacksonSerializer(ActorJackson.class));
        ObjectMapper mapper = new ObjectMapper();

        String jsonResult = mapper.registerModule(module).writer(new DefaultPrettyPrinter()).writeValueAsString(movieWithNullValue);

        Object json = mapper.readValue("{\"actors\":[{\"imdbId\":\"nm2199632\",\"dateOfBirth\":\"21-09-1982\",\"N° Film: \":3,\"filmography\":\"Apocalypto-Beatdown-Wind Walkers\"}],\"imdbID\":null}", Object.class);
        String expectedOutput = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT).writeValueAsString(json);

        Assert.assertEquals(jsonResult, expectedOutput);
    }
}
