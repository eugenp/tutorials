package com.baeldung.couchbase.spring.person;

import org.springframework.stereotype.Service;

import com.baeldung.couchbase.spring.service.JsonDocumentConverter;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;

@Service
public class PersonDocumentConverter implements JsonDocumentConverter<Person> {

    @Override
    public JsonDocument toDocument(Person p) {
        JsonObject content = JsonObject.empty().put("type", "Person").put("name", p.getName()).put("homeTown", p.getHomeTown());
        return JsonDocument.create(p.getId(), content);
    }

    @Override
    public Person fromDocument(JsonDocument doc) {
        JsonObject content = doc.content();
        Person p = new Person();
        p.setId(doc.id());
        p.setType("Person");
        p.setName(content.getString("name"));
        p.setHomeTown(content.getString("homeTown"));
        return p;
    }
}
