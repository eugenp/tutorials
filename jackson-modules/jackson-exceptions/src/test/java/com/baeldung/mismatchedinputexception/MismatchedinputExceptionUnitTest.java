package com.baeldung.mismatchedinputexception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MismatchedinputExceptionUnitTest {

    @Test
    void givenJsonString_whenDeserializingToJavaObjectWithImmutableField_thenIdIsCorrect() throws JsonProcessingException {
        String jsonString = "{\"id\":10,\"name\":\"Dog\"}";
        ObjectMapper mapper = new ObjectMapper();
        Animals animal = mapper.readValue(jsonString, Animals.class);
        assertEquals(animal.getId(),10);
    }

    @Test
    void givenJsonString_whenDeserializingToBook_thenIdIsCorrect() throws JsonProcessingException {
        String jsonString = "{\"id\":\"10\",\"title\":\"Harry Potter\"}";
        ObjectMapper mapper = new ObjectMapper();
        Book book = mapper.readValue(jsonString, Book.class);
        assertEquals(book.getId(),10);
    }


    @Test
    void givenJsonString_whenDeserializingToBookList_thenTitleIsCorrect() throws JsonProcessingException {
        String jsonString = "[{\"id\":\"10\",\"title\":\"Harry Potter\"}]";
        ObjectMapper mapper = new ObjectMapper();
        List<Book> book = mapper.readValue(jsonString, new TypeReference<List<Book>>(){});
        assertEquals(book.get(0).getTitle(),"Harry Potter");
    }

}