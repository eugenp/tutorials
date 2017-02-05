package com.baeldung.jackson.miscellaneous.mixin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import static io.restassured.path.json.JsonPath.from;
import java.io.IOException;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class OptionalTypeTest {

    ObjectMapper mapper = new ObjectMapper()
            .registerModule(new Jdk8Module());

    @Test
    public void givenOptional_whenSerializing_thenValueInJson() throws JsonProcessingException {

        // arrange
        String subTitle = "The Parish Boy's Progress";
        Book book = new Book();
        book.setTitle("Oliver Twist");
        book.setSubTitle(Optional.of(subTitle));

        // act
        String result = mapper.writeValueAsString(book);

        // assert
        assertThat(from(result).getString("subTitle")).isEqualTo(subTitle);
    }

    @Test
    public void givenEmptyOptional_whenSerializing_thenNullValue() throws JsonProcessingException {

        // arrange
        Book book = new Book();
        book.setTitle("Oliver Twist");
        book.setSubTitle(Optional.empty());

        // act
        String result = mapper.writeValueAsString(book);

        // assert
        assertThat(from(result).getString("subTitle")).isNull();
    }

    @Test
    public void givenField_whenDeserializing_thenOptionalWithValue() throws IOException {

        // arrange
        String subTitle = "The Parish Boy's Progress";
        String book = "{ \"title\": \"Oliver Twist\", \"subTitle\": \"" + subTitle + "\" }";

        // act
        Book result = mapper.readValue(book, Book.class);

        // assert
        assertThat(result.getSubTitle()).isEqualTo(Optional.of(subTitle));
    }

    @Test
    public void givenEmptyField_whenDeserializing_thenEmptyOptional() throws IOException {

        // arrange
        String book = "{ \"title\": \"Oliver Twist\", \"subTitle\": null }";

        // act
        Book result = mapper.readValue(book, Book.class);

        // assert
        assertThat(result.getSubTitle()).isEmpty();
    }
}
