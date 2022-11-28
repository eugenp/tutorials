package com.baeldung.examples.copying;

public class ShallowCopying implements CopyExample<Person> {

    @Override
    public Person copy(Person source) {
        Person johnCopy = new Person(source.getName(), source.getAge(), source.getBestFriend());
        return johnCopy;
    }

}
