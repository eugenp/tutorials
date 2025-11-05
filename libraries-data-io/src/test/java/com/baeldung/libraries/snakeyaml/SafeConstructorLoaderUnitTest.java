package com.baeldung.libraries.snakeyaml;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

class SafeConstructorLoaderUnitTest {
    
    @Test
    void givenValidYamlInput_whenParseYaml_thenReturnCustomerObject() {
        String yamlInput = "firstName: \"John\"\n" +
                "lastName: \"Doe\"\n" +
                "age: 25";

        SafeConstructorLoader parser = new SafeConstructorLoader();
        Customer result = parser.parseYaml(yamlInput);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("John", result.getFirstName());
        Assertions.assertEquals("Doe", result.getLastName());
        Assertions.assertEquals(25, result.getAge());
    }

    @Test
    void givenInvalidYamlInput_whenParseYaml_thenThrowYAMLException() {
        String yamlInput = "invalid_yaml_format";
        SafeConstructorLoader parser = new SafeConstructorLoader();

        Assertions.assertThrows(YAMLException.class, () -> {
            parser.parseYaml(yamlInput);
        });
    }

    @Test
    void givenEmptyYamlInput_whenParseYaml_thenReturnNull() {
        String yamlInput = "";
        SafeConstructorLoader parser = new SafeConstructorLoader();

        Customer result = parser.parseYaml(yamlInput);

        Assertions.assertNull(result);
    }
}
