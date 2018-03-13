package com.mybatis3.services;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mybatis3.domain.Tutor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
public class TutorServiceTest 
{
	@Autowired
	private TutorService tutorService;
	
	@BeforeClass
	public static void setup() {
		TestDataPopulator.initDatabase();
	}

	
	@Test
	public void testFindAllTutors() {
		List<Tutor> tutors = tutorService.findAllTutors();
		assertNotNull(tutors);
		for (Tutor tutor : tutors)
		{
			System.err.println(tutor);
		}
	}

	@Test
	public void testFindTutorById() {
		Tutor tutor = tutorService.findTutorById(1);
		assertNotNull(tutor);
		//System.err.println(tutor);
	}

	@Test
	public void testFindTutorByNameAndEmail() {
		Tutor tutor = tutorService.findTutorByNameAndEmail("Paul", "paul@gmail.com");
		assertNotNull(tutor);
		//System.err.println(tutor);
	}

	@Test
	public void testCreateTutor() {
		Tutor tutor = new Tutor();
		tutor.setName("siva");
		tutor.setEmail("siva@gmail.com");
		tutor = tutorService.createTutor(tutor);
		assertNotNull(tutor);
	}

	@Test
	public void testUpdateTutor() {
		Tutor tutor = new Tutor();
		tutor.setTutorId(1);
		tutor.setName("sivaprasad");
		tutor.setEmail("sivaprasad@gmail.com");
		tutor = tutorService.updateTutor(tutor);
		Tutor updTutor = tutorService.findTutorById(1);
		assertNotNull(updTutor);
		System.err.println(updTutor);
	}

	@Test
	public void testDeleteTutor() {
		boolean deleted = tutorService.deleteTutor(4);
   		assertTrue(deleted);
	}

	@Test
	public void testSelectTutorById() {
		Tutor tutor = tutorService.selectTutorById(1);
		assertNotNull(tutor);
		System.err.println(tutor);
	}

	@Test
	public void testSelectTutorWithCoursesById() {
		Tutor tutor = tutorService.selectTutorWithCoursesById(1);
		assertNotNull(tutor);
		System.err.println(tutor);
	}

}
