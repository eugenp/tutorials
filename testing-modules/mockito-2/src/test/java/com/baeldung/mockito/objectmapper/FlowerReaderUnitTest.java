package com.baeldung.mockito.objectmapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FlowerReaderUnitTest {

    @Mock
    private ObjectMapper objectMapper;

    private FlowerReader flowerReader;

    @BeforeEach
    public void setUp() {
        flowerReader = new FlowerReader(objectMapper);
    }

    @Test
    public void whenCallingWithJsonAsString_thenReturnsExpectedFlowerObject() throws JsonProcessingException {
        String flowerAsJson = "{\"name\": \"rose\", \"petals\": 100}";
        String roseName = "rose";
        Integer rosePetals = 100;
        Flower rose = new Flower(roseName, rosePetals);

        when(objectMapper.readValue(anyString(), eq(Flower.class))).thenReturn(rose);
        Flower actualRose = flowerReader.readFlower(flowerAsJson);

        assertEquals(roseName, actualRose.getName());
        assertEquals(rosePetals, actualRose.getPetals());
    }

    @Test
    public void whenCallingWithIncorrectJson_thenThrowsException() throws JsonProcessingException {
        String incorrectJson = "this is not a flower in JSON";

        when(objectMapper.readValue(anyString(), eq(Flower.class))).thenThrow(JsonProcessingException.class);

        assertThrows(RuntimeException.class, () -> flowerReader.readFlower(incorrectJson));
    }
}
