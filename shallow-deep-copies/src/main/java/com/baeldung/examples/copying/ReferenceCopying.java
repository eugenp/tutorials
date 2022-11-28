package com.baeldung.examples.copying;

public class ReferenceCopying implements CopyExample<Person>{

    @Override
    public Person copy(Person source) {
        Person target = source;
        return target;
    }

}
