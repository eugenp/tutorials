package com.baeldung.examples.copying;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DeepCopyingObjectMapper implements CopyExample<Person> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Person copy(Person source) {
        return objectMapper.convertValue(source, Person.class);
    }

}
