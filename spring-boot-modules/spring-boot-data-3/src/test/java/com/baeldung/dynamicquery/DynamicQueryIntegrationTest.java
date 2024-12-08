package com.baeldung.dynamicquery;

import com.baeldung.dynamicquery.model.QStudent;
import com.baeldung.dynamicquery.model.School;
import com.baeldung.dynamicquery.model.Student;
import com.baeldung.dynamicquery.repository.SchoolRepository;
import com.baeldung.dynamicquery.repository.StudentRepository;
import com.baeldung.dynamicquery.spec.StudentSpecification;
import com.querydsl.core.types.dsl.*;
import jakarta.inject.Inject;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("dynamicquery")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DynamicQueryIntegrationTest {

    @Inject
    private SchoolRepository schoolRepository;

    @Inject
    private StudentRepository studentRepository;

    @BeforeAll
    void setup() {
        School school1 = schoolRepository.save(School.builder()
                .name("University of West London")
                .borough("Ealing")
                .build());

        School school2 = schoolRepository.save(School.builder()
                .name("Kingston University")
                .borough("Kingston upon Thames")
                .build());

        studentRepository.saveAll(List.of(
                Student.builder().name("Emily Smith").age(20).school(school2).build(),
                Student.builder().name("James Smith").age(20).school(school1).build(),
                Student.builder().name("Maria Johnson").age(22).school(school1).build(),
                Student.builder().name("Michael Brown").age(21).school(school1).build(),
                Student.builder().name("Sophia Smith").age(22).school(school1).build()
        ));
    }

    @Test
    void givenQueryByExample_whenSelectExample_thenReturnStudentsWhoAreAge20() {
        School schoolExample = new School();
        schoolExample.setBorough("Ealing");

        Student studentExample = new Student();
        studentExample.setAge(20);
        studentExample.setName("Smith");
        studentExample.setSchool(schoolExample);

        ExampleMatcher customExampleMatcher = ExampleMatcher.matching()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.endsWith().ignoreCase());

        Example<Student> example = Example.of(studentExample, customExampleMatcher);
        List<Student> studentList = studentRepository.findAll(example);
        assertThat(studentList).hasSize(1);
    }

    @Test
    void givenQueryBySpecification_whenSelectByDsl_thenReturn() {
        Specification<Student> studentSpec = Specification
                .where(StudentSpecification.nameEndsWithIgnoreCase("smith"))
                .and(StudentSpecification.isAge(20))
                .and(StudentSpecification.isSchoolBorough("Ealing"));

        List<Student> studentList = studentRepository.findAll(studentSpec);
        assertThat(studentList).hasSize(1);
    }

    @Test
    void givenQueryByQueryDsl_whenSelectByDsl_thenReturn() {
        QStudent qStudent = QStudent.student;
        BooleanExpression predicate = qStudent.name.endsWithIgnoreCase("smith")
                .and(qStudent.age.eq(20))
                .and(qStudent.school.borough.eq("Ealing"));

        List<Student> studentList = (List<Student>) studentRepository.findAll(predicate);
        assertThat(studentList).hasSize(1);
    }

}