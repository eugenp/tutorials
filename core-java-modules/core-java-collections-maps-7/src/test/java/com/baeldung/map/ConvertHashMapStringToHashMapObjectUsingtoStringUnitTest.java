package com.baeldung.map;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConvertHashMapStringToHashMapObjectUsingtoStringUnitTest {

    @Test
    void givenValidCustomObject_whenSerializing_thenSerializedStringIsCorrect() {
        ConvertHashMapStringToHashMapObjectUsingtoString customObject = new ConvertHashMapStringToHashMapObjectUsingtoString("John", 30);
        String expectedSerializedString = "{name=John, age=30}";
        assertEquals(expectedSerializedString, customObject.toString());
    }

    @Test
    void givenValidSerializedString_whenDeserializing_thenCustomObjectIsCorrect() {
        String serializedString = "{name=Alice, age=25}";
        ConvertHashMapStringToHashMapObjectUsingtoString customObject = ConvertHashMapStringToHashMapObjectUsingtoString.deserializeCustomObject(serializedString);
        assertEquals("Alice", customObject.name);
        assertEquals(25, customObject.age);
    }

    @Test
    void givenInvalidSerializedString_whenDeserializing_thenDefaultCustomObjectIsCreated() {
        String invalidSerializedString = "{invalidString}";
        ConvertHashMapStringToHashMapObjectUsingtoString customObject = ConvertHashMapStringToHashMapObjectUsingtoString.deserializeCustomObject(invalidSerializedString);
        assertEquals("", customObject.name);
        assertEquals(-1, customObject.age);
    }
}
