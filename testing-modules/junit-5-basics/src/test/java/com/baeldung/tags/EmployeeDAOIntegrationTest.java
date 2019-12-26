package com.baeldung.tags;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.baeldung.junit.tags.example.Employee;
import com.baeldung.junit.tags.example.EmployeeDAO;
import com.baeldung.junit.tags.example.SpringJdbcConfig;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { SpringJdbcConfig.class }, loader = AnnotationConfigContextLoader.class)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class EmployeeDAOIntegrationTest {

    @Autowired
    private EmployeeDAO employeeDao;

    @Mock
    private JdbcTemplate jdbcTemplate;
    private EmployeeDAO employeeDAO;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        employeeDAO = new EmployeeDAO();
        employeeDAO.setJdbcTemplate(jdbcTemplate);
    }

    @Test
    @Tag("IntegrationTest")
    public void testAddEmployeeUsingSimpelJdbcInsert() {
        final Employee emp = new Employee();
        emp.setId(12);
        emp.setFirstName("testFirstName");
        emp.setLastName("testLastName");
        emp.setAddress("testAddress");

        Assertions.assertEquals(employeeDao.addEmplyeeUsingSimpelJdbcInsert(emp), 1);
    }

    @Test
    @Tag("UnitTest")
    public void givenNumberOfEmployeeWhenCountEmployeeThenCountMatch() {

        // given
        Mockito.when(jdbcTemplate.queryForObject(Mockito.any(String.class), Mockito.eq(Integer.class)))
                .thenReturn(1);

        // when
        int countOfEmployees = employeeDAO.getCountOfEmployees();

        // then
        Assertions.assertEquals(1, countOfEmployees);
    }

}
