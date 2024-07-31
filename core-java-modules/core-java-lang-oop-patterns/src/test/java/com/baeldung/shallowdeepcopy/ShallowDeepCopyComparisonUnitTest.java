package com.baeldung.shallowdeepcopy;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ShallowDeepCopyComparisonUnitTest {

    @Test
    public void whenDoingShallowCopyInsteadOfDeepCopy_thenExecutionTimeShouldBeSmaller() {

        final int tries = 3;
        final int copiesNumber = 10_000_000;

        Address address = new Address("Fifth Avenue", "New York");
        Teacher classMaster = new Teacher("John", "Doe");
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            students.add(new Student("First", "Student", classMaster, address));
        }

        Group original = new Group(students);
        long shallowTime = 0;
        long deepTime = 0;
        

        for (int trial = 0; trial < tries; trial++) {
            long start = System.currentTimeMillis();
            for (long i = 0; i < copiesNumber; i++) {
                new Group(original.getStudents());
            }
            shallowTime += System.currentTimeMillis() - start;

            start = System.currentTimeMillis();
            for (long i = 0; i < copiesNumber; i++) {
                new Group(original);
            }
            deepTime += System.currentTimeMillis() - start;
        }

        shallowTime /= 3;
        deepTime /= 3;

        assertTrue(shallowTime < deepTime);
    }
}
