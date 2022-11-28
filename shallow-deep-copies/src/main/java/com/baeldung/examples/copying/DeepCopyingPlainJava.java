package com.baeldung.examples.copying;

public class DeepCopyingPlainJava implements CopyExample<Person> {

    @Override
    public Person copy(Person source) {
        return source.copy();
    }

}
