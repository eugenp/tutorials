package com.baeldung.lombok.Model;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class StudentTest {

    @Test
    public void givenStudentBuilder_StudentisBuiltwithCustomEnrolledClassesSetter() {
        Student student = Student.builder()
            .name("John Doe")
            .GPA(3.0)
            .enrolledClasses(Arrays.asList("CS 101", "Math 201")).build();
        
        Assert.assertTrue(student.getEnrolledClasses().get(0).equals("CS101"));
        Assert.assertTrue(student.getEnrolledClasses().get(1).equals("Math201"));
    }
}
