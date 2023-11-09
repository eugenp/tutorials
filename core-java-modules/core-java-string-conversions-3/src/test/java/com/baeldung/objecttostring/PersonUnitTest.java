package com.baeldung.objecttostring;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PersonUnitTest {
    @Test
    public void givenObject_whenToString_thenConvert() {
        Person person = new Person("Sarah", 28);
        String expected = "Person{name='Sarah', age=28}";
        String actual = person.toString();
        assertEquals(expected, actual);
    }

    @Test
    public void givenObject_whenValueOf_thenConvert() {
        Person person = new Person("Sarah", 28);
        String expected = "Person{name='Sarah', age=28}";
        String actual = String.valueOf(person);
        assertEquals(expected, actual);
    }

    @Test
    public void givenObject_whenConcat_thenConvert() {
        Person person = new Person("Sarah", 28);
        String expected = "Person{name='Sarah', age=28}";
        String actual = "" + person;
        assertEquals(expected, actual);
    }

    @Test
    public void givenObject_whenToStringBuilder_thenConvert() {
        Person person = new Person("Sarah", 28);
        String expected = "{\"name\":\"Sarah\",\"age\":28}";
        String actual = person.toCustomString();
        assertEquals(expected, actual);
    }
}
