package com.baeldung.spring.jdbc.template.testing;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeDAO {
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int getCountOfEmployees() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM EMPLOYEE", Integer.class);
    }

    public Employee getEmployeeById(int id) {
        RowMapper<Employee> employeeRowMapper = (rs, rowNum) -> new Employee(rs.getInt("ID"), rs.getString("FIRST_NAME"), rs.getString("LAST_NAME"));

        return jdbcTemplate.queryForObject("SELECT * FROM EMPLOYEE WHERE id=?", employeeRowMapper, id);
    }

    public Employee getEmployeeByIdV2(int id) {
        RowMapper<Employee> employeeRowMapper = (rs, rowNum) -> new Employee(rs.getInt("ID"), rs.getString("FIRST_NAME"), rs.getString("LAST_NAME"));

        try {
            return jdbcTemplate.queryForObject("SELECT * FROM EMPLOYEE WHERE id=?", employeeRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

}