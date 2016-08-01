package org.baeldung.mock;

import java.util.ArrayList;
import java.util.List;

import org.baeldung.web.vo.Student;

public class MockStudentData {

	private static List<Student> studentList = new ArrayList<>();

	static {
		studentList.add(new Student("1", "Bryan", "Male", 20));
		studentList.add(new Student("2", "Ben", "Male", 22));
		studentList.add(new Student("3", "Lisa", "Female", 24));
		studentList.add(new Student("4", "Sarah", "Female", 26));
		studentList.add(new Student("5", "Jay", "Male", 20));
		studentList.add(new Student("6", "John", "Male", 22));
		studentList.add(new Student("7", "Jordan", "Male", 24));
		studentList.add(new Student("8", "Rob", "Male", 26));
		studentList.add(new Student("9", "Will", "Male", 20));
		studentList.add(new Student("10", "Shawn", "Male", 22));
		studentList.add(new Student("11", "Taylor", "Female", 24));
		studentList.add(new Student("12", "Venus", "Female", 26));
		studentList.add(new Student("13", "Vince", "Male", 20));
		studentList.add(new Student("14", "Carol", "Female", 22));
		studentList.add(new Student("15", "Joana", "Female", 24));
		studentList.add(new Student("16", "Dion", "Male", 26));
		studentList.add(new Student("17", "Evans", "Male", 20));
		studentList.add(new Student("18", "Bart", "Male", 22));
		studentList.add(new Student("19", "Jenny", "Female", 24));
		studentList.add(new Student("20", "Kristine", "Female", 26));
	}
	
	public static List<Student> getMockDataStudents(){
		return studentList;
	}
	
}
