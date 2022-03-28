package com.bealdung.deepcopy;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DeepSchoolCloneTest {
    @Test
    public void whenCloneTeacher_thenDistinctValue() throws CloneNotSupportedException {
        DeepSubject math = new DeepSubject("1", "maths");
        DeepTeacher mike = new DeepTeacher("1", "mike", math);
        // create a new teacher from mike object
        DeepTeacher molly = (DeepTeacher) mike.clone();
        molly.setSubject(new DeepSubject("1", "english"));

        assertEquals("english", molly.getSubject().name);
        assertEquals("maths", mike.getSubject().name);
    }
}