package com.baeldung.jdbcclient.dao;

import com.baeldung.jdbcclient.model.Student;
import com.baeldung.jdbcclient.model.StudentResultExtractor;
import com.baeldung.jdbcclient.model.StudentRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCountCallbackHandler;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class StudentDao {

    private static final Logger logger = LoggerFactory.getLogger(StudentDao.class);
    @Autowired
    private JdbcClient jdbcClient;

    public Integer insertWithSetParamWithNamedParamAndSqlType(Student student) {
        String sql = "INSERT INTO student (student_name, age, grade, gender, state)"
          + "VALUES (:name, :age, :grade, :gender, :state)";
        Integer noOfrowsAffected = this.jdbcClient.sql(sql)
          .param("name", student.getStudentName(), Types.VARCHAR)
          .param("age", student.getAge(), Types.INTEGER)
          .param("grade", student.getGrade(), Types.INTEGER)
          .param("gender", student.getStudentGender(), Types.VARCHAR)
          .param("state", student.getState(), Types.VARCHAR)
          .update();
        logger.info("No. of rows affected: " + noOfrowsAffected);
        return noOfrowsAffected;
    }

    public List<Student> getStudentsOfGradeStateAndGenderWithPositionalParams(int grade, String state, String gender) {
        String sql = "select student_id, student_name, age, grade, gender, state from student"
          + " where grade = ? and state = ? and gender = ?";
        return jdbcClient.sql(sql)
          .param(grade)
          .param(state)
          .param(gender)
          .query(new StudentRowMapper()).list();
    }

    public List<Student> getStudentsOfGradeStateAndGenderWithParamIndex(int grade, String state, String gender) {
        String sql = "select student_id, student_name, age, grade, gender, state from student"
          + " where grade = ? and state = ? and gender = ?";
        return jdbcClient.sql(sql)
          .param(1,grade)
          .param(2, state)
          .param(3, gender)
          .query(new StudentResultExtractor());
    }

    public Student getStudentsOfGradeStateAndGenderWithParamsInVarargs(int grade, String state, String gender) {
        String sql = "select student_id, student_name, age, grade, gender, state from student"
          + " where grade = ? and state = ? and gender = ? limit 1";
        return jdbcClient.sql(sql)
          .params(grade, state, gender)
          .query(new StudentRowMapper()).single();
    }

    public Optional<Student> getStudentsOfGradeStateAndGenderWithParamsInList(List params) {
        String sql = "select student_id, student_name, age, grade, gender, state from student"
          + " where grade = ? and state = ? and gender = ? limit 1";
        return jdbcClient.sql(sql)
          .params(params)
          .query(new StudentRowMapper()).optional();
    }


    public int getCountOfStudentsOfGradeStateAndGenderWithNamedParam(int grade, String state, String gender) {
        String sql = "select student_id, student_name, age, grade, gender, state from student"
          + " where grade = :grade and state = :state and gender = :gender";
        RowCountCallbackHandler countCallbackHandler = new RowCountCallbackHandler();
        jdbcClient.sql(sql)
          .param("grade", grade)
          .param("state", state)
          .param("gender", gender)
          .query(countCallbackHandler);
        return countCallbackHandler.getRowCount();
    }

    public List<Student> getStudentsOfGradeStateAndGenderWithParamMap(Map<String, ?> paramMap) {
        String sql = "select student_id, student_name, age, grade, gender, state from student"
          + " where grade = :grade and state = :state and gender = :gender";
        return jdbcClient.sql(sql)
          .params(paramMap)
          .query(new StudentRowMapper()).list();
    }
}
