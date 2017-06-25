package com.baeldung.commons.beanutils;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class CourseServiceTest {
    
    @Test
    public void givenCourse_whenSetValuesUsingPropertyUtil_thenReturnSetValues() 
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Course course = new Course();
        String name = "Computer Science";
        List<String> codes = Arrays.asList("CS", "CS01");
        CourseService.setValues(course, name, codes);
        
        Assert.assertEquals(name, course.getName());
        Assert.assertEquals(2, course.getCodes().size());
        Assert.assertEquals("CS", course.getCodes().get(0));
        
        CourseService.setIndexedValue(course, 1, "CS02");
        Assert.assertEquals("CS02", course.getCodes().get(1));
        
        Student student = new Student();
        String studentName = "Joe";
        student.setName(studentName);
        
        CourseService.setMappedValue(course, "ST-1", student);
        Assert.assertEquals(student, course.getEnrolledStudent("ST-1"));
        
        String accessedStudentName = CourseService.getNestedValue(course, "ST-1", "name");
        Assert.assertEquals(studentName, accessedStudentName);
    }

}
