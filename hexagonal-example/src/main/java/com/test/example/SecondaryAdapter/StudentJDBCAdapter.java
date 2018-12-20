package com.test.example.SecondaryAdapter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.test.example.domain.StudentDetail;

public class StudentJDBCAdapter {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/Test";

	// Database credentials
	static final String USER = "username";
	static final String PASS = "password";

	public void saveStudentDetailsToDatabase(StudentDetail student) {
		Connection conn = null;
		Statement stmt = null;
		try {

			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String studentName = student.getStudentName();
			String studentSpecialization = student.getStudentSpecialization();
			String insertTableSQL = "INSERT INTO Student" + "(Student_Name, Student_Specialization) VALUES" + "(?,?)";
			PreparedStatement preparedStatement = conn.prepareStatement(insertTableSQL);
			preparedStatement.setString(1, studentName);
			preparedStatement.setString(2, studentSpecialization);
			preparedStatement.executeUpdate();
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
	}// end main
}
