package com.baeldung.algorithms.boruvka;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BoruvkaUnitTest {

    private Input input;
    private static String INPUT_JSON = "/input.json";

    @Before
    public void convertInputJsonToObject() throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        StringBuilder jsonStr = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(BoruvkaMST.class.getResourceAsStream(INPUT_JSON)))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                jsonStr.append(line)
                    .append("\n");
            }
        }

        input = mapper.readValue(jsonStr.toString(), Input.class);

    }

    @Test
    public void givenInputGraph_whenBoruvkaPerformed_thenMinimumSpanningTree() throws JsonParseException, JsonMappingException, IOException {
        Graph graph = new Graph(input);
        BoruvkaMST boruvkaMST = new BoruvkaMST(graph);

        Tree mst = (Tree) boruvkaMST.getMST();

        assertEquals(30, boruvkaMST.getTotalWeight());
        assertEquals(4, mst.getEdgeCount());
    }

}
