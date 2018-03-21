package com.javacodegeeks.dao.impl;

import com.javacodegeeks.dao.JDBCEmployeeDao;
import com.javacodegeeks.domain.Employee;
import com.javacodegeeks.domain.EmployeeRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class JDBCEmployeeDaoImpl implements JDBCEmployeeDao {

    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource source) {
        this.dataSource = source;
    }

    public void insert(Employee employee) {
        String sql = "INSERT INTO employee (ID,NAME,AGE) VALUES (?,?,?)";

        jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(sql, new Object[] {
                employee.getId(), employee.getName(), employee.getAge()
        });
    }

    public Employee findById(int id) {

        String sql = "SELECT * FROM employee WHERE ID = ?";
        jdbcTemplate = new JdbcTemplate(dataSource);
        Employee employee = (Employee) jdbcTemplate.queryForObject(sql, new Object[]{id}, new EmployeeRowMapper());

        return employee;
    }
}
