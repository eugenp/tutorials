package com.baeldung.manytomany;

import com.baeldung.manytomany.model.Course;
import com.baeldung.manytomany.model.CourseRating;
import com.baeldung.manytomany.model.CourseRatingKey;
import com.baeldung.manytomany.model.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ManyToManyTestConfiguration.class)
@DirtiesContext
@Transactional
public class ManyToManyIntegrationTest {

    @PersistenceContext
    EntityManager entityManager;

    @Test
    public void contextStarted() {
    }

    @Test
    public void whenCourseRatingPersisted_thenCorrect() {
        Student student = new Student(101L);
        entityManager.persist(student);

        Course course = new Course(201L);
        entityManager.persist(course);

        CourseRating courseRating = new CourseRating();
        courseRating.setId(new CourseRatingKey());
        courseRating.setStudent(student);
        courseRating.setCourse(course);
        courseRating.setRating(100);
        entityManager.persist(courseRating);

        CourseRating persistedCourseRating = entityManager.find(CourseRating.class, new CourseRatingKey(101L, 201L));

        assertThat(persistedCourseRating, notNullValue());
        assertThat(persistedCourseRating.getStudent().getId(), is(101L));
        assertThat(persistedCourseRating.getCourse().getId(), is(201L));
    }
}
