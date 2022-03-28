package com.bealdung.shallowcopy;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ShallowSchoolCloneTest {
    @Test
    public void whenCloneTeacher_thenSameValue() throws CloneNotSupportedException {
        Subject math = new Subject("1", "maths");
        Teacher mike = new Teacher("1", "mike", math);
        // create a new teacher from mike object
        Teacher molly = (Teacher) mike.clone();

        assertEquals(mike.subject.name, molly.subject.name);
    }
}