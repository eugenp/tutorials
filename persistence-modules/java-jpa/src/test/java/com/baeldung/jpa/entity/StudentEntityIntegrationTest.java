package com.baeldung.jpa.entity;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StudentEntityIntegrationTest {

	private EntityManagerFactory emf;
	private EntityManager em;

	@Before
	public void setup() {
		emf = Persistence.createEntityManagerFactory("jpa-entity-definition");
		em = emf.createEntityManager();
	}

	@Test
	public void persistStudentThenRetrieveTheDetails() {
		Student student = createStudentWithRelevantDetails();
		persist(student);
		clearThePersistenceContext();
		List<Student> students = getStudentsFromTable();
		checkAssertionsWith(students);
	}

	@After
	public void destroy() {
		if (em != null) {
			em.close();
		}
		if (emf != null) {
			emf.close();
		}
	}

	private void clearThePersistenceContext() {
		em.clear();
	}

	private void checkAssertionsWith(List<Student> students) {
		assertEquals(1, students.size());
		Student john = students.get(0);
		assertEquals(1L, john.getId().longValue());
		assertEquals(null, john.getAge());
		assertEquals("John", john.getName());
	}

	private List<Student> getStudentsFromTable() {
		String selectQuery = "SELECT student FROM Student student";
		TypedQuery<Student> selectFromStudentTypedQuery = em.createQuery(selectQuery, Student.class);
		List<Student> students = selectFromStudentTypedQuery.getResultList();
		return students;
	}

	private void persist(Student student) {
		em.getTransaction().begin();
		em.persist(student);
		em.getTransaction().commit();
	}

	private Student createStudentWithRelevantDetails() {
		Student student = new Student();
		student.setAge(20); // the 'age' field has been annotated with @Transient
		student.setName("John");
		Date date = getDate();
		student.setBirthDate(date);
		student.setGender(Gender.MALE);
		return student;
	}

	private Date getDate() {
		LocalDate localDate = LocalDate.of(2008, 7, 20);
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

}
