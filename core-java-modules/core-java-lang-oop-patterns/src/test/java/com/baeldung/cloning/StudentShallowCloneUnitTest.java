package com.baeldung.cloning;

import org.junit.Assert;
import org.junit.Test;

public class StudentShallowCloneUnitTest {

    @Test
    public void whenUsingShallowCloning_thenCompareTwoStudent() throws CloneNotSupportedException {
        Marks marks = new Marks(98, 97, 100);
        StudentShallowClone stud1 = new StudentShallowClone(1, "Mark", marks);

        // The stud1.clone() would return an object
        // The object has to be explicitly type casted to of type  StudentShallowClone
        StudentShallowClone stud1Clone = (StudentShallowClone) stud1.clone();

        // Altering the english marks via stud1Clone would NOT change the mark for stud1
        // As they're pointing to two different objects in the heap.
        stud1Clone.getMarks()
            .setEnglish(100);

        Assert.assertEquals(stud1.getMarks()
            .getEnglish(), stud1Clone.getMarks()
            .getEnglish());

        // The assertion proves that they're pointing to the same object in the heap.
        Assert.assertEquals(stud1.getMarks(), stud1Clone.getMarks());
    }
}
