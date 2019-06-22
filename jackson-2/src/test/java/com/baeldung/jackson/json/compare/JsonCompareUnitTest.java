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

        String jsonString1 = "{\"k1\":\"v1\",\"k2\":\"v2\",\"k3\":\"v3\",\"k4\":\"v4\"}";
        String jsonString2 = "{\"k1\":\"v1\",\"k2\":\"v2\",\"k3\":\"v3\",\"k4\":\"v4\"}";
        JsonNode actualObj1 = mapper.readTree(jsonString1);
        JsonNode actualObj2 = mapper.readTree(jsonString2);

        assertTrue(actualObj1.equals(actualObj2));

    }

    @Test
    public void givenTwoJsonDataObjects_whenComparedUsingCustomComparator_thenEqual() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        String jsonString1 = "{\"k1\": 5,\"k2\":9191}";
        String jsonString2 = "{\"k1\": 5.0,\"k2\":9191}";
        JsonNode actualObj1 = mapper.readTree(jsonString1);
        JsonNode actualObj2 = mapper.readTree(jsonString2);

        assertFalse(actualObj1.equals(actualObj2));

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

        assertTrue(actualObj1.equals(cmp, actualObj2));

    }

}
