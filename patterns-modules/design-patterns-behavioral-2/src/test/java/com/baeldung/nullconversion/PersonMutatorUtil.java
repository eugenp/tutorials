package com.baeldung.nullconversion;

import java.util.function.Consumer;

public class PersonMutatorUtil {

    public static Person cloneAndMutate(Person person, Consumer<Person> mutator) {
        Person clone = person.clone();
        mutator.accept(clone);
        return clone;
    }
}
