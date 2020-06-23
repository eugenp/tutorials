package com.baeldung.architecture.hexagonal.framework.persistence.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.baeldung.architecture.hexagonal.domain.Student;

@Repository
public class H2Repository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    class StudentRowMapper implements RowMapper<Student> {
        @Override
        public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
            Student student = new Student();
            student.setId(rs.getLong("id"));
            student.setName(rs.getString("name"));
            student.setScore(rs.getFloat("score"));
            student.setPerformanceRating(rs.getString("performance_rating"));
            return student;
        }
    }

    public Optional<Student> findById(long id) {
        return Optional.of(jdbcTemplate.queryForObject(
          "select * from student where id=?",
          new Object[] { id },
          new BeanPropertyRowMapper<Student>(Student.class)));
    }

    public void deleteById(long id) {
        jdbcTemplate.update(
          "delete from student where id=?", new Object[] { id });
    }

    public Student insert(Student student) {
        jdbcTemplate.update(
          "insert into student (id, name, score, performance_rating) " 
          + "values(?, ?, ?, ?)",
          new Object[] { student.getId(), student.getName(),student.getScore(), student.getPerformanceRating() });
        return findById(student.getId()).get();
    }

    public Student update(Student student) {
        jdbcTemplate.update(
          "update student " + " set name = ?, score = ?, performance_rating = ? " 
          + " where id = ?",
          new Object[] { student.getName(), student.getScore(), student.getPerformanceRating(), student.getId() });
        return findById(student.getId()).get();
    }
}
