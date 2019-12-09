package com.baeldung.method.multiplereturnvalues;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class MultipleReturnValuesUsingWrappersUnitTest {

    private MultipleReturnValuesUsingWrappers multipleReturnValuesUsingWrappers;
    
    @Before
    public void setup() {
        this.multipleReturnValuesUsingWrappers = new MultipleReturnValuesUsingWrappers();
    }
    
    @Test
    public void test() {
        Student student = multipleReturnValuesUsingWrappers.getStudent();
        assertEquals("Alex", student.name);
        assertEquals(15, student.age);
    }
}
