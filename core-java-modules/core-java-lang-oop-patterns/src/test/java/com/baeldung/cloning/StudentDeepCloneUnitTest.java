package com.baeldung.cloning;

import org.junit.Assert;
import org.junit.Test;

public class StudentDeepCloneUnitTest {

    @Test
    public void whenUsingDeepCloning_thenCompareTwoStudents() throws CloneNotSupportedException {
        Marks marks = new Marks(98, 97, 100);
        StudentDeepClone stud1 = new StudentDeepClone(1, "Mark", marks);

        // The stud1.clone() would return an object
        // The object has to be explicitly type casted to of type  StudentShallowClone
        StudentDeepClone stud1Clone = (StudentDeepClone) stud1.clone();

        // Altering the english marks via stud1Clone would change the mark for stud1 also
        // as they're pointing to the same object in the heap.
        stud1Clone.getMarks()
            .setEnglish(100);

        Assert.assertNotEquals(stud1.getMarks()
            .getEnglish(), stud1Clone.getMarks()
            .getEnglish());
        Assert.assertNotEquals(stud1.getMarks(), stud1Clone.getMarks());
    }

}
