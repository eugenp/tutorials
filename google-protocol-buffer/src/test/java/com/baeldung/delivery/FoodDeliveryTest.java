package com.baeldung.delivery;

import io.github.venkat1701.delivery.FoodDelivery;
import io.github.venkat1701.generated.Food;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.TestInstance;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FoodDeliveryTest {
    private static final String FILE_PATH = "src/main/resources/foodfile.bin";
    private final FoodDelivery foodDelivery = new FoodDelivery();
    private Food.FoodDelivery testData;

    @BeforeEach
    void setUp() {
        testData = foodDelivery.buildData();
    }

    @Test
    void givenValidData_whenBuildData_thenShouldContainExpectedValues() {
        assertTrue(testData.getRestaurantsMap().containsKey("Pizza Place"), "Should contain 'Pizza Place'");
        assertTrue(testData.getRestaurantsMap().containsKey("Sushi Place"), "Should contain 'Sushi Place'");
        assertEquals(12.99f, testData.getRestaurantsMap().get("Pizza Place").getItemsMap().get("Margherita"),
                "Margherita price should be 12.99");

    }

    @Test
    void givenProtobufObject_whenSerializeToFile_thenFileShouldExist() {
        foodDelivery.serializeToFile(testData);
        File file = new File(FILE_PATH);
        assertTrue(file.exists(), "Serialized file should exist");
    }

    @Test
    void givenSerializedFile_whenDeserialize_thenShouldMatchOriginalData() {
        foodDelivery.serializeToFile(testData);
        Food.FoodDelivery deserializedData = foodDelivery.deserializeFromFile(testData);
        assertEquals(testData.getRestaurantsMap(), deserializedData.getRestaurantsMap(), "Deserialized data should match the original data");
    }

    @Test
    void givenDeserializedObject_whenDisplayRestaurants_thenShouldPrintCorrectOutput() {
        foodDelivery.serializeToFile(testData);
        Food.FoodDelivery deserializedData = foodDelivery.deserializeFromFile(testData);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        foodDelivery.displayRestaurants(deserializedData);
        String consoleOutput = outputStream.toString().trim();

        assertTrue(consoleOutput.contains("Restaurant: Pizza Place"), "Output should contain 'restaurant: Pizza Place'");
        assertTrue(consoleOutput.contains("Margherita costs $ 12.990000"), "Output should contain 'Margherita costs $ 12.990'");
    }

    @AfterAll
    static void cleanup() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            file.delete();
        }
    }
}
