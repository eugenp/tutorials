package com.baeldung.jackson.jsoncompare;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Comparator;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.NumericNode;
import com.fasterxml.jackson.databind.node.TextNode;

public class JsonCompareUnitTest {

    @Test
    public void givenTwoSameJsonDataObjects_whenCompared_thenAreEqual() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        String s1 = "{\"employee\": {\"id\": \"1212\",\"fullName\": \"John Miles\", \"age\": 34 }}";
        String s2 = "{\"employee\": {\"id\": \"1212\",\"age\": 34, \"fullName\": \"John Miles\" }}";

        JsonNode actualObj1 = mapper.readTree(s1);
        JsonNode actualObj2 = mapper.readTree(s2);

        assertEquals(actualObj1, actualObj2);

    }

    @Test
    public void givenTwoSameNestedJsonDataObjects_whenCompared_thenEqual() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        String s1 = "{\"employee\": {\"id\": \"1212\",\"fullName\": \"John Miles\",\"age\": 34, \"contact\":{\"email\": \"john@xyz.com\",\"phone\": \"9999999999\"} }}";
        String s2 = "{\"employee\": {\"id\": \"1212\",\"fullName\": \"John Miles\",\"age\": 34, \"contact\":{\"email\": \"john@xyz.com\",\"phone\": \"9999999999\"} }}";

        JsonNode actualObj1 = mapper.readTree(s1);
        JsonNode actualObj2 = mapper.readTree(s2);

        assertEquals(actualObj1, actualObj2);

    }

    @Test
    public void givenTwoSameListJsonDataObjects_whenCompared_thenEqual() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        String s1 = "{\"employee\": {\"id\": \"1212\",\"fullName\": \"John Miles\",\"age\": 34, \"skills\":[\"Java\", \"C++\", \"Python\"] }}";
        String s2 = "{\"employee\": {\"id\": \"1212\",\"fullName\": \"John Miles\",\"age\": 34, \"skills\":[\"Java\", \"C++\", \"Python\"] }}";

        JsonNode actualObj1 = mapper.readTree(s1);
        JsonNode actualObj2 = mapper.readTree(s2);

        assertEquals(actualObj1, actualObj2);

    }

    @Test
    public void givenTwoJsonDataObjects_whenComparedUsingCustomNumericNodeComparator_thenEqual() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        String s1 = "{\"name\": \"John\",\"score\":5.0}";
        String s2 = "{\"name\": \"John\",\"score\":5}";
        JsonNode actualObj1 = mapper.readTree(s1);
        JsonNode actualObj2 = mapper.readTree(s2);

        NumericNodeComparator cmp = new NumericNodeComparator();

        assertNotEquals(actualObj1, actualObj2);
        assertTrue(actualObj1.equals(cmp, actualObj2));

    }
    
    public class NumericNodeComparator implements Comparator<JsonNode> {
        @Override
        public int compare(JsonNode o1, JsonNode o2) {
            if (o1.equals(o2)) {
                return 0;
            }
            if ((o1 instanceof NumericNode) && (o2 instanceof NumericNode)) {
                Double d1 = ((NumericNode) o1).asDouble();
                Double d2 = ((NumericNode) o2).asDouble();
                if (d1.compareTo(d2) == 0) {
                    return 0;
                }
            }
            return 1;
        }
    }
    
    @Test
    public void givenTwoJsonDataObjects_whenComparedUsingCustomTextNodeComparator_thenEqual() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        String s1 = "{\"name\": \"JOHN\",\"score\":5}";
        String s2 = "{\"name\": \"John\",\"score\":5}";
        JsonNode actualObj1 = mapper.readTree(s1);
        JsonNode actualObj2 = mapper.readTree(s2);

        TextNodeComparator cmp = new TextNodeComparator();

        assertNotEquals(actualObj1, actualObj2);
        assertTrue(actualObj1.equals(cmp, actualObj2));

    }
    
    public class TextNodeComparator implements Comparator<JsonNode> {
        @Override
        public int compare(JsonNode o1, JsonNode o2) {
            if (o1.equals(o2)) {
                return 0;
            }
            if ((o1 instanceof TextNode) && (o2 instanceof TextNode)) {
                String s1 = ((TextNode) o1).asText();
                String s2 = ((TextNode) o2).asText();
                if (s1.equalsIgnoreCase(s2)) {
                    return 0;
                }
            }
            return 1;
        }
    }
}
