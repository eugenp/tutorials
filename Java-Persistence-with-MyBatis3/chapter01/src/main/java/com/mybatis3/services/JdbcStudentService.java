package com.mybatis3.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.mybatis3.domain.Student;


/**
 * @author Siva
 *
 */

public class JdbcStudentService
{
	
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/elearning";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "admin";

	public static void main(String[] args)
	{
		
		JdbcStudentService service = new JdbcStudentService();
		
		Student existingStudent = service.findStudentById(1);
		System.out.println(existingStudent);
		
		
		long ts = System.currentTimeMillis();//For creating unique student names
		Student newStudent = new Student(0,"student_"+ts,"student_"+ts+"@gmail.com",new Date());
		service.createStudent(newStudent);
		System.out.println(newStudent);
		
		int updateStudId = 3;
		Student updateStudent = service.findStudentById(updateStudId);
		ts = System.currentTimeMillis();//For creating unique student email
		updateStudent.setEmail("student_"+ts+"@gmail.com");
		service.updateStudent(updateStudent);
		
	}
	
	public Student findStudentById(int studId)
	{
		Student student = null;
		Connection conn = null;
		try
		{
			conn = getDatabaseConnection();
			String sql = "select * from students where stud_id=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, studId);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next())
			{
				student = new Student();
				student.setStudId(rs.getInt("stud_id"));
				student.setName(rs.getString("name"));
				student.setEmail(rs.getString("email"));
				student.setDob(rs.getDate("dob"));
			}
			
		} catch (SQLException e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			if(conn!= null){
				try {
					conn.close();
				} catch (SQLException e){ }
			}
		}
		return student;
	}
	
	public void createStudent(Student student)
	{
		Connection conn = null;
		try
		{
			conn = getDatabaseConnection();
			String sql = "INSERT INTO STUDENTS(STUD_ID,NAME,EMAIL,DOB) VALUES(?,?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, student.getStudId());
			pstmt.setString(2, student.getName());
			pstmt.setString(3, student.getEmail());
			pstmt.setDate(4, new java.sql.Date(student.getDob().getTime()));
			pstmt.executeUpdate();
			
		} catch (SQLException e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			if(conn!= null){
				try {
					conn.close();
				} catch (SQLException e){ }
			}
		}
	}
	
	public void updateStudent(Student student)
	{
		Connection conn = null;
		try
		{
			conn = getDatabaseConnection();
			conn = getDatabaseConnection();
			String sql = "UPDATE STUDENTS SET NAME=?,EMAIL=?,DOB=? WHERE STUD_ID=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, student.getName());
			pstmt.setString(2, student.getEmail());
			pstmt.setDate(3, new java.sql.Date(student.getDob().getTime()));
			pstmt.setInt(4, student.getStudId());
			pstmt.executeUpdate();
			
		} catch (SQLException e)
		{
			throw new RuntimeException(e.getCause());
		}
		finally
		{
			if(conn!= null){
				try {
					conn.close();
				} catch (SQLException e){ }
			}
		}
	}
	
	protected Connection getDatabaseConnection() throws SQLException
	{
		try
		{
			Class.forName(JdbcStudentService.DRIVER);
			return DriverManager.getConnection(JdbcStudentService.URL, 
												JdbcStudentService.USERNAME, 
												JdbcStudentService.PASSWORD);
		} catch (SQLException e)
		{
			throw e;
		} catch (Exception e)
		{
			throw new RuntimeException(e.getCause());
		} 
	}

}
