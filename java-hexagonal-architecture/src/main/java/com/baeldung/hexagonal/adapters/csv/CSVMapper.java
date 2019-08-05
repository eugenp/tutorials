package com.baeldung.hexagonal.adapters.csv;

import com.baeldung.hexagonal.ports.ContentTypeMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Iterator;

/**
 * Class turns JSON people structure to CSV format
 */
@Service("csvMapper") public class CSVMapper implements ContentTypeMapper<String> {

        @Override public String mapContent(String content) {
                String header = "name; phoneNumber";
                StringBuilder csv = new StringBuilder(header);

                ObjectMapper mapper = new ObjectMapper();

                try {
                        JsonNode jsonNode = mapper.readTree(content);
                        Iterator<JsonNode> elements = jsonNode.elements();
                        while (elements.hasNext()) {
                                JsonNode element = elements.next();
                                String name = element.get("name").asText();
                                String phoneNumber = element.get("phoneNumber").asText();
                                csv.append("\n" + name + ";" + phoneNumber);

                        }
                } catch (IOException e) {
                        e.printStackTrace();
                }

                return csv.toString();
        }
}
