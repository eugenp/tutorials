package com.baeldung.transformers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.transformers.dto.DepartmentStudentsDto;
import com.baeldung.transformers.dto.StudentDto;
import com.baeldung.transformers.entity.Course;
import com.baeldung.transformers.entity.Department;
import com.baeldung.transformers.entity.Enrollment;
import com.baeldung.transformers.entity.Student;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Transactional
@SuppressWarnings("unchecked")
@ActiveProfiles("transformers")
@SpringBootTest(classes = TransformersApplication.class)
class HibernateTransformersIntegrationTest {

    @PersistenceContext
    EntityManager em;

    @BeforeEach
    void setUp() {
        Department cs = new Department("Computer Science");
        Department math = new Department("Mathematics");
        em.persist(cs);
        em.persist(math);

        Student alice = new Student("Alice", cs);
        Student bob = new Student("Bob", cs);
        Student carol = new Student("Carol", math);
        em.persist(alice);
        em.persist(bob);
        em.persist(carol);

        Course algorithms = new Course("Algorithms", cs);
        Course calculus = new Course("Calculus", math);
        em.persist(algorithms);
        em.persist(calculus);

        em.persist(new Enrollment(alice, algorithms));
        em.persist(new Enrollment(bob, algorithms));
        em.persist(new Enrollment(carol, calculus));
    }

    @Test
    void whenUsingTupleTransformer_thenMapToStudentDto() {
        List<StudentDto> results = em.createQuery("SELECT s.id, s.name FROM Student s")
            .unwrap(Query.class)
            .setTupleTransformer((tuple, aliases) -> new StudentDto((Long) tuple[0], (String) tuple[1]))
            .getResultList();

        assertEquals(3, results.size());
        assertTrue(results.stream()
            .anyMatch(s -> s.name()
                .equals("Alice")));
    }

    @Test
    void whenUsingDistinctInJPQL_thenNoDuplicateStudents() {
        List<String> results = em.createQuery("SELECT DISTINCT s.name FROM Enrollment e JOIN e.student s", String.class)
            .getResultList();

        assertEquals(3, results.size());
        assertTrue(results.contains("Alice"));
        assertTrue(results.contains("Bob"));
        assertTrue(results.contains("Carol"));
    }

    @Test
    void whenUsingResultListTransformer_thenRemoveDuplicateStudentsFromEnrollments() {
        List<StudentDto> results = em.createQuery("SELECT s.id, s.name FROM Enrollment e JOIN e.student s", Object[].class)
            .unwrap(Query.class)
            .setTupleTransformer((tuple, aliases) -> new StudentDto((Long) tuple[0], (String) tuple[1]))
            .setResultListTransformer(list -> list.stream()
                .distinct()
                .toList())
            .getResultList();

        assertEquals(3, results.size());
        assertTrue(results.stream()
            .anyMatch(s -> s.name()
                .equals("Alice")));
        assertTrue(results.stream()
            .anyMatch(s -> s.name()
                .equals("Bob")));
        assertTrue(results.stream()
            .anyMatch(s -> s.name()
                .equals("Carol")));
    }

    @Test
    void whenUsingTupleTransformerWithAliases_thenMapSafelyByName() {
        List<StudentDto> results = em.createQuery("SELECT s.id AS studentId, s.name AS studentName FROM Student s")
            .unwrap(org.hibernate.query.Query.class)
            .setTupleTransformer((tuple, aliases) -> {
                Long id = null;
                String name = null;
                for (int i = 0; i < aliases.length; i++) {
                    switch (aliases[i]) {
                    case "studentId" -> id = (Long) tuple[i];
                    case "studentName" -> name = (String) tuple[i];
                    }
                }
                return new StudentDto(id, name);
            })
            .getResultList();

        assertEquals(3, results.size());
        assertTrue(results.stream()
            .anyMatch(s -> s.name()
                .equals("Alice")));
    }

    @Test
    void whenUsingResultListTransformer_thenGroupStudentsByDepartment() {
        List<DepartmentStudentsDto> results = (em.createQuery("SELECT d.name, s.name FROM Department d JOIN d.students s")
            .unwrap(Query.class)
            .setTupleTransformer((tuple, aliases) -> new AbstractMap.SimpleEntry<>((String) tuple[0], (String) tuple[1]))
            .setResultListTransformer(list -> ((Map<String, List<String>>) list.stream()
                .collect(Collectors.groupingBy(e -> ((Map.Entry<String, String>) e).getKey(), Collectors.mapping(e -> ((Map.Entry<String, String>) e).getValue(), Collectors.toList())))).entrySet()
                    .stream()
                    .map(e -> new DepartmentStudentsDto(e.getKey(), e.getValue()))
                    .toList())
            .getResultList());

        assertEquals(2, results.size());

        DepartmentStudentsDto csDept = results.stream()
            .filter(r -> r.department()
                .equals("Computer Science"))
            .findFirst()
            .orElseThrow();

        assertEquals(2, csDept.students()
            .size());
    }
}
