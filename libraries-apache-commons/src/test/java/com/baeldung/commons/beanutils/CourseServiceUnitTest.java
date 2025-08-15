package com.baeldung.commons.beanutils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.apache.commons.beanutils.PropertyUtils;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
 
import org.junit.Assert;
import org.junit.Test;

public class CourseServiceUnitTest {

	private static final String STUDENT_ID = "01";

    @Test
    public void givenCourse_whenGettingSimplePropertyValueUsingPropertyUtil_thenValueReturned() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Course course = new Course();
        String name = "Computer Science";
        List<String> codes = Arrays.asList("CS101","CS102");
        CourseService.setValues(course, name, codes);

		// Use getSimpleProperty to retrieve the 'name' property from the course bean
        String courseName = (String) PropertyUtils.getSimpleProperty(course, "name");

        assertEquals("Computer Science", courseName);
    }

    @Test
    public void givenCourse_whenGettingIndexedPropertyValueUsingPropertyUtil_thenValueReturned() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Course course = new Course();
        String name = "Computer Science";
        List<String> codes = Arrays.asList("CS101","CS102");
        CourseService.setValues(course, name, codes);
		// Use getIndexedProperty to retrieve the element at index 1 from the 'codes' list
        String secondCode = (String) PropertyUtils.getIndexedProperty(course, "codes[1]");
		
        assertEquals("CS102", secondCode);
    }

    @Test
    public void givenCourse_whenGettingMappedPropertyValueUsingPropertyUtil_thenValueReturned() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Course course = new Course();
        String name = "Computer Science";
        List<String> codes = Arrays.asList("CS101","CS102");
        CourseService.setValues(course, name, codes);

		// 1. Create and set a Student
        Student student = new Student();
        student.setName("John Doe");
		CourseService.setMappedValue(course, STUDENT_ID, student);
		// Use getMappedProperty to retrieve the value associated with the key '01'
        // from the 'enrolledStudent' map
        Student enrolledStudent = (Student) PropertyUtils.getMappedProperty(course, "enrolledStudent(" + STUDENT_ID + ")");
    
        assertEquals("John Doe", enrolledStudent.getName());
    }

    @Test
    public void givenCourse_whenSetValuesUsingPropertyUtil_thenReturnSetValues() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
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

    @Test
    public void givenCopyProperties_whenCopyCourseToCourseEntity_thenCopyPropertyWithSameName() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Course course = new Course();
        course.setName("Computer Science");
        course.setCodes(Arrays.asList("CS"));
        course.setEnrolledStudent("ST-1", new Student());

        CourseEntity courseEntity = new CourseEntity();

        CourseService.copyProperties(course, courseEntity);
        Assert.assertNotNull(course.getName());
	    Assert.assertNotNull(courseEntity.getName());
        Assert.assertEquals(course.getName(), courseEntity.getName());
        Assert.assertEquals(course.getCodes(), courseEntity.getCodes());
        Assert.assertNull(courseEntity.getStudent("ST-1"));
    }
    
    @Test
    public void givenNullName_whenCopyProperties_thenCopyEveryPropertyButName() throws IllegalAccessException, InvocationTargetException {
        Course originalCourse = new Course();
        originalCourse.setName(null);
        originalCourse.setCodes(Arrays.asList("CS"));
        originalCourse.setEnrolledStudent("ST-1", new Student());

        CourseEntity destCourse = new CourseEntity();
        destCourse.setName("entityName");

        IgnoreNullBeanUtilsBean ignoreNullBeanUtilsBean = new IgnoreNullBeanUtilsBean();
        ignoreNullBeanUtilsBean.copyProperties(destCourse, originalCourse);
        
        assertEquals("entityName", destCourse.getName());
        assertThat(destCourse.getCodes()).containsExactly("CS");
        assertThat(destCourse.getStudents()).isEmpty();
    }
    
}
