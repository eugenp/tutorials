package com.baeldung.method.multiplereturnvalues;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class MultipleReturnValuesUsingCollectionsUnitTest {

    private MultipleReturnValuesUsingCollections multipleReturnValuesUsingCollections;

    @Before
    public void setup() {
        this.multipleReturnValuesUsingCollections = new MultipleReturnValuesUsingCollections();
    }

    @Test
    public void test() {
        Map<String, Object> student = multipleReturnValuesUsingCollections.getStudentData();
        assertEquals("Alex", student.get("name"));
        assertEquals(15, student.get("age"));
    }
}
