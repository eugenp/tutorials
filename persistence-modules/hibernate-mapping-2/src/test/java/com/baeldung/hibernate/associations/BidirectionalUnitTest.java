// package com.baeldung.hibernate.associations;

// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
// import org.springframework.test.context.junit.jupiter.SpringExtension;

// import java.util.Arrays;
// import java.util.List;
// import com.baeldung.associations.biredirectional.*;


// import static org.assertj.core.api.Assertions.assertThat;

// @ExtendWith(SpringExtension.class)
// @DataJpaTest
// public class BidirectionalHibernateIntegrationTest {

//     @Autowired
//     private Course courseRepository;

//     @Autowired
//     private Student studentRepository;

//     @Test
//     public void whenAddingStudentsToCourse_thenCourseHasStudents() {
//         // given
//         Student student1 = new Student();
//         student1.setName("John");
//         Student student2 = new Student();
//         student2.setName("Jane");
//         studentRepository.saveAll(Arrays.asList(student1, student2));

//         Course course = new Course();
//         course.setName("History");
//         courseRepository.save(course);

//         // when
//         List<Student> students = studentRepository.findAll();
//         course.setStudents(students);
//         courseRepository.save(course);

//         // then
//         Course result = courseRepository.findById(course.getId()).get();
//         assertThat(result.getStudents()).containsExactlyInAnyOrder(student1, student2);
//     }

// }

package com.baeldung.hibernate.associations;


import com.baeldung.associations.biredirectional.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

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
