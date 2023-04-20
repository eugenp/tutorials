package com.baeldung.association;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BidirectionalUnitTest {

    @Test
    public void givenDepartmentWithEmployees_whenGetEmployees_thenReturnListWithEmployees() {
        // given
        Department department = new Department();
        Employee employee1 = new Employee();
        Employee employee2 = new Employee();
        department.getEmployees().add(employee1);
        department.getEmployees().add(employee2);

        // when
        List<Employee> result = department.getEmployees();

        // then
        assertAll("department employees",
            () -> assertEquals(2, result.size()),
            () -> assertTrue(result.contains(employee1)),
            () -> assertTrue(result.contains(employee2))
        );
    }

    @Test
    public void givenCourseWithStudents_whenGetStudents_thenReturnListWithStudents() {
        // given
        Course course = new Course();
        Student student1 = new Student();
        Student student2 = new Student();
        course.getStudents().add(student1);
        course.getStudents().add(student2);

        // when
        List<Student> result = course.getStudents();

        // then
        assertAll("course students",
            () -> assertEquals(2, result.size()),
            () -> assertTrue(result.contains(student1)),
            () -> assertTrue(result.contains(student2))
        );
    }

}
