package com.baeldung.jackson.json.compare;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Comparator;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.NumericNode;

public class JsonCompareUnitTest {

    @Test
    public void givenTwoSameJsonDataObjects_whenCompared_thenAreEqual() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        String s1 = "{\"employee\": {\"id\": \"1212\",\"fullName\": \"John Miles\", \"age\": 34 }}";
        String s2 = "{\"employee\": {\"id\": \"1212\",\"age\": 34, \"fullName\": \"John Miles\" }}";

        JsonNode actualObj1 = mapper.readTree(s1);
        JsonNode actualObj2 = mapper.readTree(s2);

        assertTrue(actualObj1.equals(actualObj2));

    }

    @Test
    public void givenTwoSameNestedJsonDataObjects_whenCompared_thenEqual() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        String s1 = "{\"employee\": {\"id\": \"1212\",\"fullName\": \"John Miles\",\"age\": 34, \"contact\":{\"email\": \"john@xyz.com\",\"phone\": \"9999999999\"} }}";
        String s2 = "{\"employee\": {\"id\": \"1212\",\"fullName\": \"John Miles\",\"age\": 34, \"contact\":{\"email\": \"john@xyz.com\",\"phone\": \"9999999999\"} }}";

        JsonNode actualObj1 = mapper.readTree(s1);
        JsonNode actualObj2 = mapper.readTree(s2);

        assertTrue(actualObj1.equals(actualObj2));

    }

    @Test
    public void givenTwoSameListJsonDataObjects_whenCompared_thenEqual() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        String s1 = "{\"employee\": {\"id\": \"1212\",\"fullName\": \"John Miles\",\"age\": 34, \"skills\":[\"Java\", \"C++\", \"Python\"] }}";
        String s2 = "{\"employee\": {\"id\": \"1212\",\"fullName\": \"John Miles\",\"age\": 34, \"skills\":[\"Java\", \"C++\", \"Python\"] }}";

        JsonNode actualObj1 = mapper.readTree(s1);
        JsonNode actualObj2 = mapper.readTree(s2);

        assertTrue(actualObj1.equals(actualObj2));

    }

    @Test
    public void givenTwoJsonDataObjects_whenComparedUsingCustomComparator_thenEqual() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        String s1 = "{\"name\": \"John\",\"score\":5.0}";
        String s2 = "{\"name\": \"John\",\"score\":5}";
        JsonNode actualObj1 = mapper.readTree(s1);
        JsonNode actualObj2 = mapper.readTree(s2);

        Comparator<JsonNode> cmp = new Comparator<JsonNode>() {
            @Override
            public int compare(JsonNode o1, JsonNode o2) {
                if (o1.equals(o2)) {
                    return 0;
                }
                if ((o1 instanceof NumericNode) && (o2 instanceof NumericNode)) {
                    double d1 = ((NumericNode) o1).asDouble();
                    double d2 = ((NumericNode) o2).asDouble();
                    if (d1 == d2) {
                        return 0;
                    }
                }
                return 1;
            }
        };

        assertFalse(actualObj1.equals(actualObj2));
        assertTrue(actualObj1.equals(cmp, actualObj2));

    }

}
