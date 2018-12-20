package com.test.example.port;

import com.test.example.SecondaryAdapter.StudentJDBCAdapter;
import com.test.example.domain.StudentDetail;

public class StudentServiceAPIImpl implements StudentServiceAPI {

	@Override
	public void enterStudentDetails(String studentName, String studentSpecialization) {
		StudentDetail student = new StudentDetail(studentName, studentSpecialization);
		StudentJDBCAdapter dbAdapter = new StudentJDBCAdapter();
		dbAdapter.saveStudentDetailsToDatabase(student);
	}

}
