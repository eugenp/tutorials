package com.baeldung.jdbcClient;

import com.baeldung.jdbcClient.dao.StudentDao;
import com.baeldung.jdbcClient.model.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@Sql(value = "/com.baeldung.jdbcClient/student.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/com.baeldung.jdbcClient/drop_student.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(classes = JdbcClientDemoApplication.class)
@TestPropertySource(locations = {"classpath:com.baeldung.jdbcClient/application.properties"})
public class JdbcClientUnitTest {
    private static final Logger logger = LoggerFactory.getLogger(JdbcClientUnitTest.class);

    @Autowired
    private StudentDao studentDao;

    @Test
    public void givenJdbcClient_whenInsertWithNamedParamAndSqlType_thenSuccess() {
        logger.info("testing invoked successfully");
        Student student = getSampleStudent("Johny Dep", 8, 4, "Male", "New York");
        assertEquals(1, studentDao.insertWithSetParamWithNamedParamAndSqlType(student));
    }

    @Test
    public void givenJdbcClient_whenQueryWithPositionalParams_thenSuccess() {
        logger.info("testing invoked successfully");
        List<Student> students = studentDao.getStudentsOfGradeStateAndGenderWithPositionalParams(
          1, "New York", "Male");
        logger.info("number of students fetched " + students.size());
        assertEquals(6, students.size());
    }

    @Test
    public void givenJdbcClient_whenQueryWithParamsInVarargs_thenSuccess() {
        logger.info("testing invoked successfully");
        Student student = studentDao.getStudentsOfGradeStateAndGenderWithParamsInVarargs(
          1, "New York", "Male");
        assertNotNull(student);
    }

    @Test
    public void givenJdbcClient_whenQueryWithParamsInList_thenSuccess() {
        logger.info("testing invoked successfully");
        List params = List.of(1, "New York", "Male");
        Optional<Student> optional = studentDao.getStudentsOfGradeStateAndGenderWithParamsInList(params);
        if(optional.isPresent()) {
            assertNotNull(optional.get());
        } else {
            assertThrows(NoSuchElementException.class, () -> optional.get());
        }
    }

    @Test
    public void givenJdbcClient_whenQueryWithParamsIndex_thenSuccess() {
        logger.info("testing invoked successfully");
        List<Student> students = studentDao.getStudentsOfGradeStateAndGenderWithParamIndex(
          1, "New York", "Male");
        assertEquals(6, students.size());
    }
    @Test
    public void givenJdbcClient_whenQueryWithNamedParam_thenSuccess() {
        logger.info("testing invoked successfully");
        Integer count = studentDao.getCountOfStudentsOfGradeStateAndGenderWithNamedParam(
          1, "New York", "Male");
        logger.info("number of students fetched " + count);
        assertEquals(6, count);
    }
    @Test
    public void givenJdbcClient_whenQueryWithParamMap_thenSuccess() {
        logger.info("testing invoked successfully");
        Map<String, ?> paramMap = Map.of(
          "grade", 1,
          "gender", "Male",
          "state", "New York"
        );
        List<Student> students = studentDao.getStudentsOfGradeStateAndGenderWithParamMap(paramMap);
        logger.info("number of students fetched " + students.size());
        assertEquals(6, students.size());
    }

    private Student getSampleStudent(String name, int age, int grade, String gender, String state) {
        Student student = new Student();
        student.setStudentName(name);
        student.setStudentGender(gender);
        student.setAge(age);
        student.setGrade(grade);
        student.setState(state);
        return student;
    }
}
