package com.baeldung.hexgonal.repo;


import com.baeldung.hexgonal.data.Employee;
import lombok.extern.slf4j.Slf4j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;


@Slf4j
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private static final String INSERT_EMPLOYEE_SQL = "INSERT INTO employees" + " (id, firstName, lastName, code) VALUES " + " (?, ?, ?, ?);";
    private static final String DELETE_EMPLOYEE_SQL = "DELETE FROM employees WHERE id = '%s'";

    @Override
    public void save(Employee employee) {
        try (Connection connection = MySQLConnectionFactory.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EMPLOYEE_SQL)) {
            preparedStatement.setString(1, employee.getId()
                .toString());
            preparedStatement.setString(2, employee.getFirstName());
            preparedStatement.setString(3, employee.getLastName());
            preparedStatement.setString(4, employee.getCode());

            log.debug(preparedStatement.toString());
            preparedStatement.executeUpdate();
            log.debug("Employee {} created in database", employee.getId()
                .toString());
        } catch (SQLException e) {
            log.error("SQL exception during save", e);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = MySQLConnectionFactory.getConnection(); Statement statement = connection.createStatement();) {
            statement.execute(String.format(DELETE_EMPLOYEE_SQL, id));
            log.debug("Employee {} deleted from database", id);
        } catch (SQLException e) {
            log.error("SQL exception during delete", e);
        }
    }

}
