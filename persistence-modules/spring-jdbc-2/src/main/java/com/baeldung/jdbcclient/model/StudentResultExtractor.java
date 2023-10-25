package com.baeldung.jdbcclient.model;

import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentResultExtractor implements ResultSetExtractor<List<Student>> {
    @Override
    public List<Student> extractData(ResultSet rs) throws SQLException {
        List<Student> students = new ArrayList<Student>();
        while(rs.next()) {
            Student student = new Student();
            student.setStudentId(rs.getInt("student_id"));
            student.setStudentName(rs.getString("student_name"));
            student.setAge(rs.getInt("age"));
            student.setStudentGender(rs.getString("gender"));
            student.setGrade(rs.getInt("grade"));
            student.setState(rs.getString("state"));
            students.add(student);
        }
        return students;
    }
}
