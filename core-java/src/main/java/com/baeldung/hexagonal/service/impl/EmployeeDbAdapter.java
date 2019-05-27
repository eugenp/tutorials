package com.baeldung.hexagonal.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.baeldung.hexagonal.entity.Employee;
import com.baeldung.hexagonal.service.EmployeePort;

public class EmployeeDbAdapter implements EmployeePort {

    public List<Employee> getEmployees() {
        List<Employee> empList = new ArrayList<>();

        Connection conn = createConnection();

        if (conn != null) {
            try {
                PreparedStatement ps = conn.prepareStatement("select * from employee");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    empList.add(new Employee(rs.getLong(0), rs.getString(1)));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return empList;
    }

    private Connection createConnection() {
        Connection con = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "oracle");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return con;

    }

}
