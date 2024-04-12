package com.baeldung.spring.jdbc.replacedeprecated;

import com.baeldung.spring.jdbc.replacedeprecated.model.Student;
import com.baeldung.spring.jdbc.replacedeprecated.model.StudentResultExtractor;
import com.baeldung.spring.jdbc.replacedeprecated.model.StudentRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCountCallbackHandler;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentDaoWithPreferredJdbcTemplateMethods {
    JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Student> getStudentsOfAgeAndGender(Integer age, String gender) {
        String sql = "select student_id, student_name, age, gender, grade, state from student where age= ? and gender = ?";
        return jdbcTemplate.query(sql, new StudentRowMapper(), age, gender);
    }

    public List<Student> getStudentsOfAgeGenderAndGrade(Integer age, String gender, Integer grade) {
        String sql = "select student_id, student_name, age, gender, grade, state from student where age= ? and gender = ? and grade = ?";
        return jdbcTemplate.query(sql, new StudentRowMapper(), age, gender, grade);
    }

    public List<Student> getStudentsOfGradeAndState(Integer grade, String state) {
        String sql = "select student_id, student_name, age, gender, grade, state from student where grade = ? and state = ?";
        return jdbcTemplate.query(sql, new StudentResultExtractor(), grade, state);
    }

    public Student getStudentOfStudentIDAndGrade(Integer studentID, Integer grade) {
        String sql = "select student_id, student_name, age, gender, grade, state from student where student_id = ? and grade = ?";

        return jdbcTemplate.queryForObject(sql, new StudentRowMapper(), studentID, grade);
    }

    public Integer getCountOfGenderInAGrade(String gender, Integer grade) {
        String sql = "select count(1) as total from student where gender = ? and grade = ?";

        return jdbcTemplate.queryForObject(sql, Integer.class, gender, grade);
    }

    public Integer getCountOfStudentsInAGradeFromAState(Integer grade, String state) {
        String sql = "select student_id, student_name, age, gender, grade, state from student where grade = ? and state = ?";

        RowCountCallbackHandler countCallbackHandler = new RowCountCallbackHandler();
        jdbcTemplate.query(sql, countCallbackHandler, grade, state);
        return countCallbackHandler.getRowCount();
    }
}
