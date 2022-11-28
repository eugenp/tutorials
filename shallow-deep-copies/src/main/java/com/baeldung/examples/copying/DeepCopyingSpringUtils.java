package com.baeldung.examples.copying;

import org.springframework.util.SerializationUtils;

public class DeepCopyingSpringUtils implements CopyExample<Person> {

    @Override
    public Person copy(Person source) {
        return (Person) SerializationUtils.deserialize(
                SerializationUtils.serialize(source)
        );
    }

}
