package com.baeldung.criteria;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Connection;
import java.util.List;

import jakarta.persistence.*;
import jakarta.persistence.criteria.*;

import javax.naming.InitialContext;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.*;

class CriteriaQueryTest {

    private static EntityManagerFactory emf;

    private static EntityManager em;

    @BeforeEach
    void setup() throws Exception {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:jakartaPersistenceApiTest");
        dataSource.setUsername("sa");

        InitialContext ctx = new InitialContext();
        ctx.bind("java:comp/env/jdbc/SchoolData", dataSource);

        emf = createEntityManagerFactory();
        emf.getSchemaManager()
            .create(true);

        em = emf.createEntityManager();

        emf.runInTransaction(em -> {
            em.persist((new School(1, "Greenwood Elementary School")));
            em.persist((new School(2, "Riverside Middle School")));
            em.persist((new School(3, "Hillcrest High School")));

            // School 1 – Greenwood Elementary School
            em.persist(new Student(1, 1, "Alice Johnson"));
            em.persist(new Student(2, 1, "Benjamin Lee"));
            em.persist(new Student(3, 1, "Clara Martinez"));

            // School 2 – Riverside Middle School
            em.persist(new Student(4, 2, "Daniel Smith"));
            em.persist(new Student(5, 2, "Emily Brown"));
            em.persist(new Student(6, 2, "Frank Wilson"));

            // School 3 – Hillcrest High Schoo
            em.persist(new Student(7, 3, "Grace Taylor"));
            em.persist(new Student(8, 3, "Henry Anderson"));
            em.persist(new Student(9, 3, "Isabella Thomas"));
        });
    }

    @AfterEach
    void tearDown() {
        emf.runInTransaction(em -> em.runWithConnection(connection -> {
            try (var stmt = ((Connection) connection).createStatement()) {
                stmt.execute("delete from student");
                stmt.execute("delete from school");
            } catch (Exception e) {
                Assertions.fail("JDBC operation failed: " + e.getMessage());
            }
        }));
        emf.close();
    }

    private EntityManagerFactory createEntityManagerFactory() {
        return new PersistenceConfiguration("SchoolData")
            .jtaDataSource("java:comp/env/jdbc/SchoolData")
            .managedClass(School.class)
            .managedClass(Student.class)
            .property("hibernate.show_sql", true)
            .property("hibernate.format_sql", true)
            .createEntityManagerFactory();
    }

    @Test
    void whenJoin_throwsIllegalArgumentException() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<School> cq = cb.createQuery(School.class);
        Root<School> schoolRoot = cq.from(School.class);

        assertThrows(IllegalArgumentException.class, () -> {
            Join<School, Student> studentJoin = schoolRoot.join("students");
        });
    }

    @Test
    void whenSubQuery_thenReturnsASchool() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<School> query = cb.createQuery(School.class);
        Root<School> schoolRoot = query.from(School.class);
        Subquery<Long> subquery = query.subquery(Long.class);
        Root<Student> studentRoot = subquery.from(Student.class);
        subquery.select(studentRoot.get("schoolId"))
            .where(cb.equal(studentRoot.get("name"), "Benjamin Lee"));
        query.select(schoolRoot)
            .where(schoolRoot.get("id").in(subquery));

        // when
        List<School> schools = em.createQuery(query).getResultList();

        // then
        assertThat(schools).hasSize(1);
        assertThat(schools.get(0).getName()).isEqualTo("Greenwood Elementary School");
    }

    @Test
    void whenCrossJoin_thenReturnsASchool() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<School> query = cb.createQuery(School.class);
        Root<School> schoolRoot = query.from(School.class);
        Root<Student> studentRoot = query.from(Student.class);
        Predicate joinCondition = cb.equal(schoolRoot.get("id"), studentRoot.get("schoolId"));
        Predicate studentName = cb.equal(studentRoot.get("name"), "Benjamin Lee");
        query.select(schoolRoot)
            .where(cb.and(joinCondition, studentName));

        // when
        List<School> schools = em.createQuery(query).getResultList();

        // then
        assertThat(schools).hasSize(1);
        assertThat(schools.get(0).getName()).isEqualTo("Greenwood Elementary School");
    }

    @Test
    void whenTupleSelect_thenReturnsASchoolWithStudent() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = cb.createTupleQuery();
        Root<School> schoolRoot = query.from(School.class);
        Root<Student> studentRoot = query.from(Student.class);
        Predicate joinCondition = cb.equal(schoolRoot.get("id"), studentRoot.get("schoolId"));
        Predicate studentName = cb.equal(studentRoot.get("name"), "Benjamin Lee");
        query.select(cb.tuple(schoolRoot,studentRoot))
            .where(cb.and(joinCondition, studentName));

        // when
        List<Tuple> tuples = em.createQuery(query).getResultList();

        // then
        assertThat(tuples).hasSize(1);

        // and
        Tuple firstTuple = tuples.get(0);
        School school = firstTuple.get(0, School.class);
        Student student = firstTuple.get(1, Student.class);
        assertThat(school.getName()).isEqualTo("Greenwood Elementary School");
        assertThat(student.getName()).isEqualTo("Benjamin Lee");
    }

}
