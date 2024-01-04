package com.baeldung.calcite;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.calcite.adapter.java.ReflectiveSchema;
import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.schema.Schema;
import org.apache.calcite.schema.SchemaPlus;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baledung.calcite.model.CompanySchema;
import com.baledung.calcite.model.Department;
import com.baledung.calcite.model.Employee;

public class CalciteUnitTest {
    Logger logger = LoggerFactory.getLogger(CalciteUnitTest.class);
    static CompanySchema companySchema = new CompanySchema();

    @Test
    void whenCsvSchema_thenQuerySuccess() throws SQLException {
        Properties info = new Properties();
        info.put("model", getPath("model.json"));
        try(Connection connection = DriverManager.getConnection("jdbc:calcite:", info)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from trades.trade");

            assertEquals(3, resultSet.getMetaData().getColumnCount());

            List<Integer> tradeIds = new ArrayList<>();
            while(resultSet.next()) {
                tradeIds.add(resultSet.getInt("tradeid"));
            }

            assertEquals(3, tradeIds.size());
        }
    }

    private String getPath(String model) {
        URL url = ClassLoader.getSystemClassLoader().getResource(model);
        logger.info("path fetched :" + url.getPath());
        return url.getPath();
    }

    @BeforeAll
    static void setup() {
        Department dept1 = new Department("HR", "Human Resource");
        Department dept2 = new Department("MKT", "Marketing");
        Department dept3 = new Department("FIN", "Finance");

        Employee emp1 = new Employee("Tom", "1234", "HR");
        Employee emp2 = new Employee("Harry", "39731", "FIN");
        Employee emp3 = new Employee("Danny", "45632", "FIN");
        Employee emp4 = new Employee("Jenny", "78654", "MKT");

        companySchema.departments = new Department[]{dept1, dept2, dept3};
        companySchema.employees = new Employee[]{emp1, emp2, emp3, emp4};
    }

    @Test
    void whenQueryEmployeesObject_thenQuerySuccess() throws SQLException {
        Properties info = new Properties();
        info.setProperty("lex", "JAVA");
        Connection connection = DriverManager.getConnection("jdbc:calcite:", info);
        CalciteConnection calciteConnection = connection.unwrap(CalciteConnection.class);
        SchemaPlus rootSchema = calciteConnection.getRootSchema();
        Schema schema = new ReflectiveSchema(companySchema);
        rootSchema.add("company", schema);
        Statement statement = calciteConnection.createStatement();
        String query = "select dept.deptName, count(emp.id) "
            + "from company.employees as emp "
            + "join company.departments as dept "
            + "on (emp.deptId = dept.deptId) "
            + "group by dept.deptName";

        assertDoesNotThrow(() -> {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                logger.info("Dept Name:" + resultSet.getString(1)
                    + " No. of employees:" + resultSet.getInt(2));
            }
        });
    }
}
