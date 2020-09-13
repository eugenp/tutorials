package com.baeldung.spring.jdbc.template.inclause;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class EmployeeDAO {
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<Employee> getEmployeesFromIdListNamed(List<Integer> ids) {
        SqlParameterSource parameters = new MapSqlParameterSource("ids", ids);
        List<Employee> employees = namedJdbcTemplate.query(
          "SELECT * FROM EMPLOYEE WHERE id IN (:ids)",
          parameters,
          (rs, rowNum) -> new Employee(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name")));

        return employees;
    }

    public List<Employee> getEmployeesFromIdList(List<Integer> ids) {
        String inSql = String.join(",", Collections.nCopies(ids.size(), "?"));
        List<Employee> employees = jdbcTemplate.query(
          String.format("SELECT * FROM EMPLOYEE WHERE id IN (%s)", inSql),
          ids.toArray(),
          (rs, rowNum) -> new Employee(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name")));

        return employees;
    }

    public List<Employee> getEmployeesFromLargeIdList(List<Integer> ids) {
        jdbcTemplate.execute("CREATE TEMPORARY TABLE IF NOT EXISTS employee_tmp (id INT NOT NULL)");

        List<Object[]> employeeIds = new ArrayList<>();
        for (Integer id : ids) {
            employeeIds.add(new Object[] { id });
        }
        jdbcTemplate.batchUpdate("INSERT INTO employee_tmp VALUES(?)", employeeIds);

        List<Employee> employees = jdbcTemplate.query(
          "SELECT * FROM EMPLOYEE WHERE id IN (SELECT id FROM employee_tmp)",
          (rs, rowNum) -> new Employee(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name")));

        jdbcTemplate.update("DELETE FROM employee_tmp");

        return employees;
    }
}
