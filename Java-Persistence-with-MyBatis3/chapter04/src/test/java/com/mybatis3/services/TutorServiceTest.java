package com.mybatis3.services;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mybatis3.domain.Tutor;

public class TutorServiceTest 
{

	private static TutorService tutorService;
	
	@BeforeClass
	public static void setup() {
		tutorService = new TutorService();
		TestDataPopulator.initDatabase();
	}
	
	@AfterClass
	public static void teardown() {
		tutorService = null;
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
		System.err.println(tutor);
	}

	@Test
	public void testFindTutorByNameAndEmail() {
		Tutor tutor = tutorService.findTutorByNameAndEmail("Paul", "paul@gmail.com");
		assertNotNull(tutor);
		System.err.println(tutor);
	}

	@Test
	public void testCreateTutor() {
		Tutor tutor = new Tutor();
		tutor.setName("siva");
		tutor.setEmail("siva@gmail.com");
		tutor = tutorService.createTutor(tutor);
		assertNotNull(tutor);
		System.err.println(tutor.getTutorId());
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
