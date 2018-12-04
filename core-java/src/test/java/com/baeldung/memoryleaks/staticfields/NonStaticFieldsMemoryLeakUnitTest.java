package com.baeldung.memoryleaks.staticfields;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

public class NonStaticFieldsMemoryLeakUnitTest {
    public List<Double> list = new ArrayList<>();
 
    public void populateList() {
        for (int i = 0; i < 10000000; i++) {
            list.add(Math.random());
        }
        System.out.println("Debug Point 2");
    }
 
    @Test
    @Ignore // Test deliberately ignored as memory leak tests consume lots of resources
    public void givenNonStaticLargeList_whenPopulatingList_thenListGarbageCollected() {
        System.out.println("Debug Point 1");
        new NonStaticFieldsMemoryLeakUnitTest().populateList();
        System.out.println("Debug Point 3");
    }
}
