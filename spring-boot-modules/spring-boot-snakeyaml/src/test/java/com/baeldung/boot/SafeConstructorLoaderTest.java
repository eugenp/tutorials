package com.baeldung.boot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.error.YAMLException;

class SafeConstructorLoaderTest {
    
    @Test
    void testParseYaml_ValidInput() {
        String yamlInput = "name: John Doe\nage: 30";
        SafeConstructorLoader parser = new SafeConstructorLoader();

        MyCustomClass result = parser.parseYaml(yamlInput);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("John Doe", result.getName());
        Assertions.assertEquals(30, result.getAge());
    }

    @Test
    void testParseYaml_InvalidInput() {
        String yamlInput = "invalid_yaml_format";
        SafeConstructorLoader parser = new SafeConstructorLoader();

        Assertions.assertThrows(YAMLException.class, () -> {
            parser.parseYaml(yamlInput);
        });
    }

    @Test
    void testParseYaml_EmptyInput() {
        String yamlInput = "";
        SafeConstructorLoader parser = new SafeConstructorLoader();

        MyCustomClass result = parser.parseYaml(yamlInput);

        Assertions.assertNull(result);
    }
}
