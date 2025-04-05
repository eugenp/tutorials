package com.baeldung.mapinprotobuf;

import com.baeldung.generated.Food;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.junit.jupiter.api.TestInstance;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FoodDeliveryUnitTest {
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
    void givenDeserializedObject_whenDisplayRestaurants_thenShouldLogCorrectOutput() {
        foodDelivery.serializeToFile(testData);
        Food.FoodDelivery deserializedData = foodDelivery.deserializeFromFile(testData);
        Logger logger = Logger.getLogger(FoodDelivery.class.getName());
        TestLogHandler testHandler = new TestLogHandler();
        logger.addHandler(testHandler);
        logger.setUseParentHandlers(false);
        foodDelivery.displayRestaurants(deserializedData);
        List<String> logs = testHandler.getLogs();
        assertTrue(logs.stream().anyMatch(log -> log.contains("Restaurant: Pizza Place")),
                "Log should contain 'Restaurant: Pizza Place'");
        assertTrue(logs.stream().anyMatch(log -> log.contains("Margherita costs $ 12.99")),
                "Log should contain 'Margherita costs $ 12.99'");
    }

    @AfterAll
    static void cleanup() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            file.delete();
        }
    }

    static class TestLogHandler extends Handler {
        private final List<String> logMessages = new ArrayList<>();
        @Override
        public void publish(LogRecord record) {
            if (record.getLevel().intValue() >= Level.INFO.intValue()) {
                logMessages.add(record.getMessage());
            }
        }
        @Override public void flush() {}
        @Override public void close() throws SecurityException {}

        public List<String> getLogs() {
            return logMessages;
        }
    }
}
