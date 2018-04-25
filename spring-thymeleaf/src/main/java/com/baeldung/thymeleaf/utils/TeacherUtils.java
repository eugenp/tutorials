package com.baeldung.thymeleaf.utils;

import java.util.ArrayList;
import java.util.List;

import com.baeldung.thymeleaf.model.Teacher;

public class TeacherUtils {

	private static List<Teacher> teachers = new ArrayList<Teacher>();

	public static List<Teacher> buildTeachers() {
		if (teachers.isEmpty()) {
			Teacher teacher1 = new Teacher();
			teacher1.setId(2001);
			teacher1.setName("Jane Doe");
			teacher1.setGender("F");
			teacher1.setActive(true);
			teacher1.getCourses().add("Mathematics");
			teacher1.getCourses().add("Physics");

			teachers.add(teacher1);

			Teacher teacher2 = new Teacher();
			teacher2.setId(2002);
			teacher2.setName("Lazy Dude");
			teacher2.setGender("M");
			teacher2.setActive(false);
			teacher2.setAdditionalSkills("emergency responder");

			teachers.add(teacher2);

			Teacher teacher3 = new Teacher();
			teacher3.setId(2002);
			teacher3.setName("Micheal Jordan");
			teacher3.setGender("M");
			teacher3.setActive(true);
			teacher3.getCourses().add("Sports");

			teachers.add(teacher3);

		}

		return teachers;
	}

}
