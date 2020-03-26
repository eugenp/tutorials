package com.baeldung.hexagonaljava.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Component;

import com.baeldung.hexagonaljava.entity.Student;

@Primary
@Component
public class StudentResultJdbcRepoImpl implements StudentResultRepo {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void save(Student student) {
        jdbcTemplate.update("insert into student (id, name) " + "values(?,  ?)", new Object[] { student.getId(), student.getName() });
        insertResult(student);
    }

    public void insertResult(Student student) {
        String insertQuery = "insert into " + "studentresult " + "(subject,marks,id) " + "values " + "(?,?,?)";
        for (final Map.Entry<String, Double> entry : student.getMarks()
          .entrySet()) {
            this.jdbcTemplate.batchUpdate(insertQuery, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(final PreparedStatement ps, final int i) throws SQLException {
                    ps.setString(1, entry.getKey());
                    ps.setDouble(2, entry.getValue());
                    ps.setInt(3, student.getId());
                }

                public int getBatchSize() {
                    return student.getMarks()
                      .size();
                }
            });
        }
    }

    @Override
    public Student getStudent(Integer id) {
        String selectQuery = "select * from ( select * from student where id = ? ) s left join studentresult on s.id = studentresult.id";
        Student student = new Student();
        jdbcTemplate.query(selectQuery, new Object[] { id }, new RowCallbackHandler() {
            public void processRow(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    if (student.getId() == null) {
                        student.setId(rs.getInt("id"));
                        student.setName(rs.getString("name"));
                        student.setMarks(new HashMap<String, Double>());
                    }
                    String subject = rs.getString("subject");
                    Double marks = rs.getDouble("marks");
                    student.getMarks()
                      .put(subject, marks);
                }
            }
        });
        return student;
    }
}
