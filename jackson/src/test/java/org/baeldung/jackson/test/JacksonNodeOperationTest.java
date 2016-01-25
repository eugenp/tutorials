package org.baeldung.jackson.node;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JacksonNodeOperationTest {
    private static ObjectMapper mapper = new ObjectMapper();

    @Test
    public void giveAnObject_whenConvertingIntoANode_thenCorrect() {
        MyBean fromValue = new MyBean(2016, "baeldung.com");

        JsonNode node = mapper.valueToTree(fromValue);

        assertEquals(2016, node.get("id").intValue());
        assertEquals("baeldung.com", node.get("name").textValue());
    }

    @Test
    public void givenANode_whenWritingOutAsAJsonString_thenCorrect() throws IOException {
        final String pathToTestFile = "node_to_json_test.json";
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

    @Test
    public void givenANode_whenConvertingIntoAnObject_thenCorrect() throws JsonProcessingException {
        JsonNode node = mapper.createObjectNode();
        ((ObjectNode) node).put("id", 2016);
        ((ObjectNode) node).put("name", "baeldung.com");

        MyBean toValue = mapper.treeToValue(node, MyBean.class);

        assertEquals(2016, toValue.getId());
        assertEquals("baeldung.com", toValue.getName());
    }

    @Test
    public void givenANode_whenAddingIntoATree_thenCorrect() throws IOException {
        JsonNode rootNode = ExampleStructure.getExampleRoot();
        ObjectNode addedNode = ((ObjectNode) rootNode).putObject("address");
        addedNode.put("city", "Seattle").put("state", "Washington").put("country", "United States");

        assertFalse(rootNode.path("address").isMissingNode());
        assertEquals("Seattle", rootNode.path("address").path("city").textValue());
        assertEquals("Washington", rootNode.path("address").path("state").textValue());
        assertEquals("United States", rootNode.path("address").path("country").textValue());
    }

    @Test
    public void givenANode_whenModifyingIt_thenCorrect() throws IOException {
        String newString = "{\"nick\": \"cowtowncoder\"}";
        JsonNode newNode = mapper.readTree(newString);

        JsonNode rootNode = ExampleStructure.getExampleRoot();
        ((ObjectNode) rootNode).set("name", newNode);

        assertFalse(rootNode.path("name").path("nick").isMissingNode());
        assertEquals("cowtowncoder", rootNode.path("name").path("nick").textValue());
    }

    @Test
    public void givenANode_whenRemovingFromATree_thenCorrect() throws IOException {
        JsonNode rootNode = ExampleStructure.getExampleRoot();
        ((ObjectNode) rootNode).remove("company");

        assertTrue(rootNode.path("company").isMissingNode());
    }

}
