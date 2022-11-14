package com.baeldung.deepshallow;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import com.baeldung.deepshallow.model.CourseDeep;
import com.baeldung.deepshallow.model.CourseShallow;
import com.baeldung.deepshallow.model.UniversityDeep;
import com.baeldung.deepshallow.model.UniversityShallow;

@SpringBootTest
class PostApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void testShallowCopy(){
		CourseShallow course = new CourseShallow("CS101", "Introduction to Computer Programing");
		UniversityShallow university = new UniversityShallow("Dar es salaam University", "Tanzania", course);
		UniversityShallow universityCopy = university.clone();
		university.getCourse().setCode("CS102");
		Assertions.assertEquals(universityCopy.getCourse().getCode(), university.getCourse().getCode());
	}

	@Test
	public void testDeepCopy(){
		CourseDeep course = new CourseDeep("TE101", "Digital Telecommunication");
		UniversityDeep universityDeep = new UniversityDeep("Dar es salaam University", "Tanzania", course);
		UniversityDeep universityDeepCopy = universityDeep.clone();
		universityDeep.getCourse().setCode("TE102");
		Assertions.assertNotEquals(universityDeep.getCourse().getCode(), universityDeepCopy.getCourse().getCode());
	}
}
