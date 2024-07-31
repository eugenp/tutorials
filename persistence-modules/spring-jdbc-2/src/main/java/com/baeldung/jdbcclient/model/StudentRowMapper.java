package com.baeldung.jdbcclient.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentRowMapper  implements RowMapper<Student> {
    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        Student student = new Student();
        student.setStudentId(rs.getInt("student_id"));
        student.setStudentName(rs.getString("student_name"));
        student.setAge(rs.getInt("age"));
        student.setStudentGender(rs.getString("gender"));
        student.setGrade(rs.getInt("grade"));
        student.setState(rs.getString("state"));
        return student;
    }
}
