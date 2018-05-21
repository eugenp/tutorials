package com.baeldung.adapter;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;

import com.baeldung.jsonb.Person;

public class PersonAdapter implements JsonbAdapter<Person, JsonObject> {

    @Override
    public JsonObject adaptToJson(Person p) throws Exception {
        return Json.createObjectBuilder()
            .add("id", p.getId())
            .add("name", p.getName())
            .build();
    }

    @Override
    public Person adaptFromJson(JsonObject adapted) throws Exception {
        Person person = new Person();
        person.setId(adapted.getInt("id"));
        person.setName(adapted.getString("name"));
        return person;
    }
}