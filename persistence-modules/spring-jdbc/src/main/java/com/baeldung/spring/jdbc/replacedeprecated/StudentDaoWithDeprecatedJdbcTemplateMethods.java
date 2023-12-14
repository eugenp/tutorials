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
public class StudentDaoWithDeprecatedJdbcTemplateMethods {
    JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Student> getStudentsOfAgeAndGender(Integer age, String gender) {
        String sql = "select student_id, student_name, age, gender, grade, state from student where age= ? and gender = ?";
        Object[] args = {age, gender};
        return jdbcTemplate.query(sql, new StudentRowMapper(),args);
    }

    public List<Student> getStudentsOfAgeGenderAndGrade(Integer age, String gender, Integer grade) {
        String sql = "select student_id, student_name, age, gender, grade, state from student where age= ? and gender = ? and grade = ?";
        Object[] args = {age, gender, grade};
        return jdbcTemplate.query(sql, new StudentRowMapper(), args);
    }

    public List<Student> getStudentsOfGradeAndState(Integer grade, String state) {
        String sql = "select student_id, student_name, age, gender, grade, state from student where grade = ? and state = ?";
        Object[] args = {grade, state};
        return jdbcTemplate.query(sql, new StudentResultExtractor(), args);
    }

    public Student getStudentOfStudentIDAndGrade(Integer studentID, Integer grade) {
        String sql = "select student_id, student_name, age, gender, grade, state from student where student_id = ? and grade = ?";
        Object[] args = {studentID, grade};

        return jdbcTemplate.queryForObject(sql,new StudentRowMapper(), args);
    }

    public Integer getCountOfStudentsInAGradeFromAState(Integer grade, String state) {
        String sql = "select student_id, student_name, age, gender, grade, state from student where grade = ? and state = ?";
        Object[] args = {grade, state};
        RowCountCallbackHandler countCallbackHandler = new RowCountCallbackHandler();
        jdbcTemplate.query(sql, countCallbackHandler, args);
        return countCallbackHandler.getRowCount();
    }
}
