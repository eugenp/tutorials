package com.baeldung.commons.dbutils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

public class EmployeeHandler extends BeanListHandler<Employee> {

    private Connection connection;

    public EmployeeHandler(Connection con) {
        super(Employee.class, new BasicRowProcessor(new BeanProcessor(getColumnsToFieldsMap())));
        this.connection = con;
    }

    @Override
    public List<Employee> handle(ResultSet rs) throws SQLException {
        List<Employee> employees = super.handle(rs);

        QueryRunner runner = new QueryRunner();
        BeanListHandler<Email> handler = new BeanListHandler<>(Email.class);
        String query = "SELECT * FROM email WHERE employeeid = ?";
        for (Employee employee : employees) {
            List<Email> emails = runner.query(connection, query, handler, employee.getId());
            employee.setEmails(emails);
        }
        return employees;
    }

    public static Map<String, String> getColumnsToFieldsMap() {
        Map<String, String> columnsToFieldsMap = new HashMap<>();
        columnsToFieldsMap.put("FIRST_NAME", "firstName");
        columnsToFieldsMap.put("LAST_NAME", "lastName");
        columnsToFieldsMap.put("HIRED_DATE", "hiredDate");
        return columnsToFieldsMap;
    }
}
