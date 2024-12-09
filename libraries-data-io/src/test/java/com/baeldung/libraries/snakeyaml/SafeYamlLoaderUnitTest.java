package com.baeldung.libraries.snakeyaml;

import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.constructor.ConstructorException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SafeYamlLoaderUnitTest {

    @Test
    void givenValidYamlContent_whenLoadYamlSafely_thenReturnParsedMap() {
        SafeYamlLoader safeYamlLoader = new SafeYamlLoader();
        String yamlContent = "key: value\nnested:\n  subkey: subvalue";

        Map<String, Object> result = safeYamlLoader.loadYamlSafely(yamlContent);

        assertNotNull(result);
        assertEquals("value", result.get("key"));
        assertTrue(result.containsKey("nested"));
    }

    @Test
    void givenExploitPayload_whenLoadYamlSafely_thenThrowConstructorException() {
        String exploitPayload = "!!java.util.date\n";

        SafeYamlLoader yamlLoader = new SafeYamlLoader();

        // SafeConstructor prevents deserialization of arbitrary objects
        ConstructorException exception = assertThrows(ConstructorException.class, () -> {
            yamlLoader.loadYamlSafely(exploitPayload);
        });

        // Assert that the exception message mentions the inability to determine a constructor for the tag
        assertTrue(exception.getMessage().contains("could not determine a constructor for the tag"),
                "Exception message should indicate inability to determine a constructor for the tag");
    }

    @Test
    void givenValidYamlInput_whenLoadYamlSafely_thenReturnParsedMap() {
        String validYaml = "key: value\nanotherKey: 123";

        SafeYamlLoader yamlLoader = new SafeYamlLoader();
        Map<String, Object> result = yamlLoader.loadYamlSafely(validYaml);

        assertEquals("value", result.get("key"));
        assertEquals(123, result.get("anotherKey"));
    }

}
