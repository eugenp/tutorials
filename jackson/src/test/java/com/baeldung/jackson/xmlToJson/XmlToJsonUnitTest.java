package com.baeldung.jackson.xmlToJson;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.IOException;

public class XmlToJsonUnitTest {

    @Test
    public void givenAnXML_whenUseDataBidingToConvertToJSON_thenReturnDataOK() {
        String flowerXML = "<Flower><name>Poppy</name><color>RED</color><petals>9</petals></Flower>";

        try {
            XmlMapper xmlMapper = new XmlMapper();
            Flower poppy = xmlMapper.readValue(flowerXML, Flower.class);

            assertEquals(poppy.getName(), "Poppy");
            assertEquals(poppy.getColor(), Color.RED);
            assertEquals(poppy.getPetals(), new Integer(9));

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(poppy);

            assertEquals(json, "{\"name\":\"Poppy\",\"color\":\"RED\",\"petals\":9}");
            System.out.println(json);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenAnXML_whenUseATreeConvertToJSON_thenReturnDataOK() {
        String flowerXML = "<Flower><name>Poppy</name><color>RED</color><petals>9</petals></Flower>";

        try {
            XmlMapper xmlMapper = new XmlMapper();
            JsonNode node = xmlMapper.readTree(flowerXML.getBytes());

            ObjectMapper jsonMapper = new ObjectMapper();
            String json = jsonMapper.writeValueAsString(node);

            System.out.println(json);

            assertEquals(json, "{\"name\":\"Poppy\",\"color\":\"RED\",\"petals\":\"9\"}");
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
