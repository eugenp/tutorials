package com.baeldung.libraries.flexypool;

import com.baeldung.libraries.hikaricp.Employee;
import com.vladmihalcea.flexypool.FlexyPoolDataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class FlexypoolDemoApplication {

    private static FlexyPoolDataSource<HikariDataSource> poolDataSource;

    public FlexypoolDemoApplication(FlexyPoolDataSource<HikariDataSource> poolDataSource) {
        FlexypoolDemoApplication.poolDataSource = poolDataSource;
    }

    public static List<Employee> getEmployees() throws SQLException {
        String SQL_QUERY = "select * from emp";
        List<Employee> employees;
        try (Connection con = poolDataSource.getConnection(); PreparedStatement pst = con.prepareStatement(SQL_QUERY); ResultSet rs = pst.executeQuery();) {
            employees = new ArrayList<>();
            Employee employee;
            while (rs.next()) {
                employee = new Employee();
                employee.setEmpNo(rs.getInt("empno"));
                employee.setEname(rs.getString("ename"));
                employee.setJob(rs.getString("job"));
                employee.setMgr(rs.getInt("mgr"));
                employee.setHiredate(rs.getDate("hiredate"));
                employee.setSal(rs.getInt("sal"));
                employee.setComm(rs.getInt("comm"));
                employee.setDeptno(rs.getInt("deptno"));
                employees.add(employee);
            }
        }
        return employees;
    }

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(FlexypoolDemoApplication.class, args);
        List<Employee> employees = getEmployees();
        System.out.println(employees);
    }

}
