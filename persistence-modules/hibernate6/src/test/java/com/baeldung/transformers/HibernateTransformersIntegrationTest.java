package com.baeldung.transformers;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        em.persist(new Enrollment(alice, calculus));
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
            .allMatch(s -> s.id() != null && s.name() != null));
    }

    @Test
    void whenUsingTupleTransformerWithAliases_thenMapSafelyByName() {
        List<StudentDto> results = em.createQuery("SELECT s.id AS studentId, s.name AS studentName FROM Student s")
            .unwrap(Query.class)
            .setTupleTransformer((tuple, aliases) -> {
                Map<String, Object> row = IntStream.range(0, aliases.length)
                    .boxed()
                    .collect(Collectors.toMap(i -> aliases[i], i -> tuple[i]));

                return new StudentDto((Long) row.get("studentId"), (String) row.get("studentName"));
            })
            .getResultList();

        assertEquals(3, results.size());
        assertTrue(results.stream()
            .allMatch(s -> s.id() != null && s.name() != null));
    }

    @Test
    void whenUsingDistinctInJPQL_thenNoDuplicateStudents() {
        List<String> results = em.createQuery("SELECT DISTINCT s.name FROM Enrollment e JOIN e.student s", String.class)
            .getResultList();

        assertEquals(3, results.size());
    }

    @Test
    void whenUsingResultListTransformer_thenRemoveDuplicateStudentsFromEnrollments() {
        List<StudentDto> results = em.createQuery("SELECT s.id, s.name FROM Enrollment e JOIN e.student s")
            .unwrap(Query.class)
            .setTupleTransformer((tuple, aliases) -> new StudentDto((Long) tuple[0], (String) tuple[1]))
            .setResultListTransformer(list -> list.stream()
                .distinct()
                .toList())
            .getResultList();

        assertEquals(3, results.size());
    }

    @Test
    void whenUsingResultListTransformer_thenGroupStudentsByDepartment() {
        List<DepartmentStudentsDto> results = em.createQuery("SELECT d.name, s.name FROM Department d JOIN d.students s")
            .unwrap(Query.class)
            .setTupleTransformer((tuple, aliases) -> new AbstractMap.SimpleEntry<>((String) tuple[0], (String) tuple[1]))
            .setResultListTransformer(list -> ((List<Map.Entry<String, String>>) list).stream()
                .collect(groupingBy(Map.Entry::getKey, mapping(Map.Entry::getValue, toList())))
                .entrySet()
                .stream()
                .map(e -> new DepartmentStudentsDto(e.getKey(), e.getValue()))
                .toList())
            .getResultList();

        assertEquals(2, results.size());
    }
}
