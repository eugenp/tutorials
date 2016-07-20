package com.baeldung.couchbase.person;

import org.springframework.stereotype.Service;

import com.baeldung.couchbase.service.JsonDocumentConverter;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;

@Service
public class FluentPersonDocumentConverter implements JsonDocumentConverter<Person> {

    @Override
    public JsonDocument toDocument(Person p) {
        JsonObject content = JsonObject.empty()
                .put("type", "Person")
                .put("name", p.getName())
                .put("homeTown", p.getHomeTown());
        return JsonDocument.create(p.getId(), content);
    }

    @Override
    public Person fromDocument(JsonDocument doc) {
        JsonObject content = doc.content();
        return Person.Builder.newInstance()
            .id(doc.id())
            .type("Person")
            .name(content.getString("name"))
            .homeTown(content.getString("homeTown"))
            .build();
    }
}
