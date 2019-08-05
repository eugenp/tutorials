package com.baeldung.hexagonal.adapters.list;

import com.baeldung.hexagonal.ports.ContentTypeMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class turns people JSON to List of @PeopleList type
 */
@Service("listMapper") public class ListMapper implements ContentTypeMapper<PeopleList> {

        @Override public PeopleList mapContent(String content) {
                ObjectMapper mapper = new ObjectMapper();

                List<Person> people = new ArrayList<>();
                try {
                        JsonNode jsonNode = mapper.readTree(content);
                        Iterator<JsonNode> elements = jsonNode.elements();
                        while (elements.hasNext()) {
                                JsonNode element = elements.next();
                                String name = element.get("name").asText();
                                String phoneNumber = element.get("phoneNumber").asText();
                                people.add(new Person(name, phoneNumber));

                        }
                } catch (IOException e) {
                        e.printStackTrace();
                }
                return new PeopleList(people);
        }
}
