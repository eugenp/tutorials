package com.mybatis3.services;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mybatis3.domain.Course;
import com.mybatis3.domain.Tutor;



public class TutorServiceTest 
{
	private static TutorService tutorService;
	
	@BeforeClass
	public static void setup()
	{
		TestDataPopulator.initDatabase();
		tutorService = new TutorService();
	}
	
	@AfterClass
	public static void teardown()
	{
		tutorService = null;
	}
	
	
	@Test
	public void testFindTutorById() {
		Tutor tutor = tutorService.findTutorById(1);
		Assert.assertNotNull(tutor);
		List<Course> courses = tutor.getCourses();
		Assert.assertNotNull(courses);
		for (Course course : courses) 
		{
			Assert.assertNotNull(course);
			System.out.println(course);
		}
	}
	
	
}
