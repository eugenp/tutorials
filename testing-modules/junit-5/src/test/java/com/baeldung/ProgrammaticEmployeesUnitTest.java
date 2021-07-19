package com.baeldung;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.baeldung.helpers.Employee;
import com.baeldung.extensions.EmployeeDaoParameterResolver;
import com.baeldung.extensions.EmployeeDatabaseSetupExtension;
import com.baeldung.extensions.EnvironmentExtension;
import com.baeldung.helpers.EmployeeJdbcDao;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({ EnvironmentExtension.class, EmployeeDaoParameterResolver.class })
public class ProgrammaticEmployeesUnitTest {

    private EmployeeJdbcDao employeeDao;

    @RegisterExtension static EmployeeDatabaseSetupExtension DB =
      new EmployeeDatabaseSetupExtension("jdbc:h2:mem:AnotherDb;DB_CLOSE_DELAY=-1", "org.h2.Driver", "sa", "");

    public ProgrammaticEmployeesUnitTest(EmployeeJdbcDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Test
    public void whenAddEmployee_thenGetEmployee() throws SQLException {
        Employee emp = new Employee(1, "john");
        employeeDao.add(emp);
        assertEquals(1, employeeDao.findAll().size());
    }

    @Test
    public void whenGetEmployees_thenEmptyList() throws SQLException {
        assertEquals(0, employeeDao.findAll().size());
    }
}
