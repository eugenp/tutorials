package com.baeldung.jacksonannotation.deserialization.jsondeserialize;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;

import static io.restassured.path.json.JsonPath.from;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Source code github.com/eugenp/tutorials
 *
 * @author Alex Theedom www.baeldung.com
 * @version 1.0
 */
public class JsonDeserializeUnitTest {

    @Test
    public void whenDeserializingUsingJsonDeserialize_thenCorrect() throws IOException {

        // arrange
        String bookJson = "{\"id\":\"957c43f2-fa2e-42f9-bf75-6e3d5bb6960a\",\"title\":\"Effective Java\",\"authors\":[{\"id\":\"9bcd817d-0141-42e6-8f04-e5aaab0980b6\",\"firstName\":\"Joshua\",\"lastName\":\"Bloch\"}],\"price\":0,\"published\":\"25-12-2017 13:30:25\",\"pages\":null,\"isbn\":null}";

        // act
        Book book = new ObjectMapper().readerFor(Book.class).readValue(bookJson);

        // assert
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        assertThat(from(bookJson).getString("published")).isEqualTo(df.format(book.getPublished()));

    }
}