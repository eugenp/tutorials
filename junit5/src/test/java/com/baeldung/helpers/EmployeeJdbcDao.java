package com.baeldung.helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EmployeeJdbcDao {

    private Connection con;

    public EmployeeJdbcDao(Connection con) {
        this.con = con;
    }

    public void createTable() throws SQLException {
        String createQuery = "CREATE TABLE employees(id long primary key, firstName varchar(50))";
        PreparedStatement pstmt = con.prepareStatement(createQuery);

        pstmt.execute();
    }

    public void add(Employee emp) throws SQLException {
        String insertQuery = "INSERT INTO employees(id, firstName) VALUES(?,?)";
        PreparedStatement pstmt = con.prepareStatement(insertQuery);
        pstmt.setLong(1, emp.getId());
        pstmt.setString(2, emp.getFirstName());

        pstmt.executeUpdate();

    }

    public List<Employee> findAll() throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM employees";
        PreparedStatement pstmt = con.prepareStatement(query);

        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Employee emp = new Employee(rs.getLong("id"), rs.getString("firstName"));
            employees.add(emp);
        }

        return employees;
    }
}