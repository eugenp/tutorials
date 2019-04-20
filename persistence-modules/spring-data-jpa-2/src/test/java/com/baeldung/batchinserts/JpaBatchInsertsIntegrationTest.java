package com.baeldung.batchinserts;

import static com.baeldung.batchinserts.TestObjectHelper.createSchool;
import static com.baeldung.batchinserts.TestObjectHelper.createStudent;

import com.baeldung.batchinserts.model.School;
import com.baeldung.batchinserts.model.Student;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ActiveProfiles("batchinserts")
public class JpaBatchInsertsIntegrationTest {

    @PersistenceContext
    private EntityManager entityManager;

    private static final int BATCH_SIZE = 5;

    @Test
    public void whenInsertingSingleTypeOfEntity_thenCreatesSingleBatch() {
        for (int i = 0; i < 10; i++) {
            School school = createSchool(i);
            entityManager.persist(school);
        }

        entityManager.flush();
    }

    @Test
    public void whenFlushingAfterBatch_ThenClearsMemory() {
        for (int i = 0; i < 10; i++) {
            if (i > 0 && i % BATCH_SIZE == 0) {
                entityManager.flush();
                entityManager.clear();
            }

            School school = createSchool(i);
            entityManager.persist(school);
        }

        entityManager.flush();
    }

    @Test
    public void whenThereAreMultipleEntities_ThenCreatesNewBatch() {
        for (int i = 0; i < 10; i++) {
            if (i > 0 && i % BATCH_SIZE == 0) {
                entityManager.flush();
                entityManager.clear();
            }

            School school = createSchool(i);
            entityManager.persist(school);
            Student firstStudent = createStudent(school);
            Student secondStudent = createStudent(school);
            entityManager.persist(firstStudent);
            entityManager.persist(secondStudent);
        }

        entityManager.flush();
    }

    @Test
    public void whenUpdatingEntities_thenCreatesBatch() {
        for (int i = 0; i < 10; i++) {
            School school = createSchool(i);
            entityManager.persist(school);
        }

        entityManager.flush();

        TypedQuery<School> schoolQuery = entityManager.createQuery("SELECT s from School s", School.class);
        List<School> allSchools = schoolQuery.getResultList();

        for (School school : allSchools) {
            school.setName("Updated_" + school.getName());
        }

        entityManager.flush();
    }
}
