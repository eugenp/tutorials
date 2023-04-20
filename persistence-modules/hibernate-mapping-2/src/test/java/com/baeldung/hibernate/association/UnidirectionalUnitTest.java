package com.baeldung.association;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UnidirectionalUnitTest {
    
    private final TestEntityManager entityManager;

    public UnidirectionalUnitTest(TestEntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Test
    public void givenDepartmentWithEmployees_whenFindById_thenDepartmentWithEmployeesReturned() {
        // given
        Employee employee1 = new Employee();
        Employee employee2 = new Employee();
        Department department = new Department();
        department.setEmployees(List.of(employee1, employee2));
        entityManager.persist(department);
        entityManager.flush();

        // when
        Department foundDepartment = entityManager.find(Department.class, department.getId());

        // then
        assertThat(foundDepartment).isEqualTo(department);
        assertThat(foundDepartment.getEmployees()).containsExactly(employee1, employee2);
    }

    @Test
    public void givenStudentWithCourse_whenFindById_thenStudentWithCourseReturned() {
        // given
        Course course = new Course();
        entityManager.persist(course);
        entityManager.flush();
        Student student = new Student();
        student.setCourse(course);
        entityManager.persist(student);
        entityManager.flush();

        // when
        Student foundStudent = entityManager.find(Student.class, student.getId());

        // then
        assertThat(foundStudent).isEqualTo(student);
        assertThat(foundStudent.getCourse()).isEqualTo(course);
    }

    @Test
    public void givenEmployeeWithParkingSpot_whenFindById_thenEmployeeWithParkingSpotReturned() {
        // given
        ParkingSpot parkingSpot = new ParkingSpot();
        entityManager.persist(parkingSpot);
        entityManager.flush();
        Employee employee = new Employee();
        employee.setParkingSpot(parkingSpot);
        entityManager.persist(employee);
        entityManager.flush();

        // when
        Employee foundEmployee = entityManager.find(Employee.class, employee.getId());

        // then
        assertThat(foundEmployee).isEqualTo(employee);
        assertThat(foundEmployee.getParkingSpot()).isEqualTo(parkingSpot);
    }

    @Test
    public void givenBookWithAuthors_whenFindById_thenBookWithAuthorsReturned() {
        // given
        Author author1 = new Author();
        Author author2 = new Author();
        entityManager.persist(author1);
        entityManager.persist(author2);
        entityManager.flush();
        Book book = new Book();
        book.setAuthors(Set.of(author1, author2));
        entityManager.persist(book);
        entityManager.flush();

        // when
        Book foundBook = entityManager.find(Book.class, book.getId());

        // then
        assertThat(foundBook).isEqualTo(book);
        assertThat(foundBook.getAuthors()).containsExactly(author1, author2);
    }

}
