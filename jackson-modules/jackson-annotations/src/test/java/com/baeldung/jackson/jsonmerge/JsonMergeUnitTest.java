package com.baeldung.jackson.jsonmerge;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

public class JsonMergeUnitTest {

    @Test
    public void givenAnObjectAndJson_whenUsingJsonMerge_thenExpectUpdateInPOJO() throws JsonProcessingException {
        String newData = "{\"favouriteLanguage\":\"Java\",\"keyboard\":{\"style\":\"Mechanical\"}}";
        ProgrammerAnnotated programmerToUpdate = new ProgrammerAnnotated("John", "C++", new Keyboard("Membrane", "US"));

        ObjectMapper objectMapper = new ObjectMapper();
        ProgrammerAnnotated update = objectMapper.readerForUpdating(programmerToUpdate)
          .readValue(newData);

        assert (update.getFavouriteLanguage()).equals("Java");
        // Only works with annotation
        assert (update.getKeyboard()
          .getLayout()).equals("US");
    }

    @Test
    public void givenAnObjectAndJson_whenNotUsingJsonMerge_thenExpectNoUpdateInPOJO() throws JsonProcessingException {
        String newData = "{\"favouriteLanguage\":\"Java\",\"keyboard\":{\"style\":\"Mechanical\"}}";
        ProgrammerNotAnnotated programmerToUpdate = new ProgrammerNotAnnotated("John", "C++", new Keyboard("Membrane", "US"));

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectReader objectReader = objectMapper.readerForUpdating(programmerToUpdate);
        ProgrammerNotAnnotated update = objectReader.readValue(newData);

        assert (update.getFavouriteLanguage()).equals("Java");
        assertNull(update.getKeyboard()
          .getLayout());
    }

    @Test
    public void givenAnObjectWithAMap_whenUsingJsonMerge_thenExpectAllFieldsInMap() throws JsonProcessingException {
        String newData = "{\"stringPairs\":{\"field1\":\"value1\",\"field2\":\"value2\"}}";
        Map<String, String> map = new HashMap<>();
        map.put("field3", "value3");
        ObjectWithMap objectToUpdateWith = new ObjectWithMap("James", map);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWithMap update = objectMapper.readerForUpdating(objectToUpdateWith)
          .readValue(newData);

        assertTrue(update.getStringPairs()
          .containsKey("field1"));
        assertTrue(update.getStringPairs()
          .containsKey("field2"));
        assertTrue(update.getStringPairs()
          .containsKey("field3"));
    }
}
