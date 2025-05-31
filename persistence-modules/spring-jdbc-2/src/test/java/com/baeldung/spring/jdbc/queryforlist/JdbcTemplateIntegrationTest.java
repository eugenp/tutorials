package com.baeldung.spring.jdbc.queryforlist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.IncorrectResultSetColumnCountException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

@JdbcTest
@Sql(value = { "init-student-data.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class JdbcTemplateIntegrationTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void whenUsingQueryForListToGetStudentList_thenGotException() {
        assertThrows(IncorrectResultSetColumnCountException.class, () -> jdbcTemplate.queryForList("SELECT * FROM STUDENT_TBL", Student.class));
    }

    @Test
    void whenUsingQueryForListToSingleColumn_thenCorrect() {
        List<String> names = jdbcTemplate.queryForList("SELECT NAME FROM STUDENT_TBL", String.class);
        assertEquals(List.of("Kai", "Eric", "Kevin", "Liam"), names);

        List<Integer> ids = jdbcTemplate.queryForList("SELECT ID FROM STUDENT_TBL", Integer.class);
        assertEquals(List.of(1, 2, 3, 4), ids);
    }

    @Test
    void whenUsingQueryForListToGetMap_thenCorrect() {
        List<Map<String, Object>> nameMajorRowMaps = jdbcTemplate.queryForList("SELECT NAME, MAJOR FROM STUDENT_TBL");

        // @formatter:off
        assertEquals(List.of(
          Map.of("NAME", "Kai", "MAJOR", "Computer Science"),
          Map.of("NAME", "Eric", "MAJOR", "Computer Science"),
          Map.of("NAME", "Kevin", "MAJOR", "Banking"),
          Map.of("NAME", "Liam", "MAJOR", "Law")
        ), nameMajorRowMaps);
        // @formatter:on

        List<Map<String, Object>> rowMaps = jdbcTemplate.queryForList("SELECT * FROM STUDENT_TBL");

        // @formatter:off
        assertEquals(List.of(
          Map.of("ID", 1, "NAME", "Kai", "MAJOR", "Computer Science"),
          Map.of("ID", 2, "NAME", "Eric", "MAJOR", "Computer Science"),
          Map.of("ID", 3, "NAME", "Kevin", "MAJOR", "Banking"),
          Map.of("ID", 4, "NAME", "Liam", "MAJOR", "Law")
        ), rowMaps);
        // @formatter:on
    }

    @Test
    void whenUsingQueryWithRowMapperToGetStudentList_thenCorrect() {
        // @formatter:off
        List<Student> expected = List.of(
          new Student(1, "Kai", "Computer Science"),
          new Student(2, "Eric", "Computer Science"),
          new Student(3, "Kevin", "Banking"),
          new Student(4, "Liam", "Law")
        );
        // @formatter:on

        List<Student> students = jdbcTemplate.query("SELECT * FROM STUDENT_TBL", new BeanPropertyRowMapper<>(Student.class));

        assertEquals(expected, students);
    }

}