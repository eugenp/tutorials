package com.baeldung.jackson.node.operations;

import static org.junit.Assert.*;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class NodeToJsonTest {
    final String pathToTestFile = "node_to_json_test.json";
    private static ObjectMapper mapper = new ObjectMapper();

    @Test
    public void givenANode_whenWritingOutAsAJsonString_thenCorrect() throws IOException {
        char[] characterBuffer = new char[50];

        JsonNode node = mapper.createObjectNode();
        ((ObjectNode) node).put("id", 2016);
        ((ObjectNode) node).put("name", "baeldung.com");

        try (FileWriter outputStream = new FileWriter(pathToTestFile)) {
            mapper.writeValue(outputStream, node);
        }

        try (FileReader inputStreamForAssertion = new FileReader(pathToTestFile)) {
            inputStreamForAssertion.read(characterBuffer);
        }
        String textContentOfTestFile = new String(characterBuffer);

        assertThat(textContentOfTestFile, containsString("2016"));
        assertThat(textContentOfTestFile, containsString("baeldung.com"));

        Files.delete(Paths.get(pathToTestFile));
    }
}
