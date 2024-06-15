package com.baeldung.testhashcode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class HahCodeUnitTest {
    @Test
    public void givenObject_whenTestingHashCodeConsistency_thenConsistentHashCodeReturned() {
        MyClass obj = new MyClass("value");
        int hashCode1 = obj.hashCode();
        int hashCode2 = obj.hashCode();
        assertEquals(hashCode1, hashCode2);
    }

    @Test
    public void givenTwoEqualObjects_whenTestingHashCodeEquality_thenEqualHashCodesReturned() {
        MyClass obj1 = new MyClass("value");
        MyClass obj2 = new MyClass("value");
        assertEquals(obj1.hashCode(), obj2.hashCode());
    }

    @Test
    public void givenMultipleObjects_whenTestingHashCodeDistribution_thenEvenDistributionOfHashCodes() {
        List<MyClass> objects = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            objects.add(new MyClass("value" + i));
        }

        Set<Integer> hashCodes = new HashSet<>();
        for (MyClass obj : objects) {
            hashCodes.add(obj.hashCode());
        }

        assertEquals(objects.size(), hashCodes.size(), 10);
    }
}