package com.baeldung.spring.jdbc;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import junit.framework.Assert;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ResultSetLiveTest {

    private final Employee expectedEmployee1 = new Employee(1, "John", 1000.0, "Developer");

    private final Employee updatedEmployee1 = new Employee(1, "John", 1100.0, "Developer");

    private final Employee expectedEmployee2 = new Employee(2, "Chris", 925.0, "DBA");

    private final int rowCount = 2;

    private static Connection dbConnection;

    @BeforeClass
    public static void setup() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/myDB?noAccessToProcedureBodies=true", "user1", "pass");
        String tableSql = "CREATE TABLE IF NOT EXISTS employees (emp_id int PRIMARY KEY AUTO_INCREMENT, name varchar(30), position varchar(30), salary double)";
        try (Statement stmt = dbConnection.createStatement()) {
            stmt.execute(tableSql);
            try (PreparedStatement pstmt = dbConnection.prepareStatement("INSERT INTO employees(name, position, salary) values ('John', 'Developer', 1000.0)")) {
                pstmt.executeUpdate();
            }
        }
    }

    @Test
    public void givenDbConnectionA_whenRetreiveByColumnNames_thenCorrect() throws SQLException {
        Employee employee = null;
        try (PreparedStatement pstmt = dbConnection.prepareStatement("select * from employees"); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String name = rs.getString("name");
                Integer empId = rs.getInt("emp_id");
                Double salary = rs.getDouble("salary");
                String position = rs.getString("position");
                employee = new Employee(empId, name, salary, position);
            }
        }

        assertEquals("Employee information retreived by column names.", expectedEmployee1, employee);
    }

    @Test
    public void givenDbConnectionB_whenRetreiveByColumnIds_thenCorrect() throws SQLException {
        Employee employee = null;
        try (PreparedStatement pstmt = dbConnection.prepareStatement("select * from employees"); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Integer empId = rs.getInt(1);
                String name = rs.getString(2);
                String position = rs.getString(3);
                Double salary = rs.getDouble(4);
                employee = new Employee(empId, name, salary, position);
            }
        }

        assertEquals("Employee information retreived by column ids.", expectedEmployee1, employee);
    }

    @Test
    public void givenDbConnectionD_whenInsertRow_thenCorrect() throws SQLException {
        int rowCount = 0;
        try (PreparedStatement pstmt = dbConnection.prepareStatement("select * from employees", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE); ResultSet rs = pstmt.executeQuery()) {
            rs.moveToInsertRow();
            rs.updateString("name", "Chris");
            rs.updateString("position", "DBA");
            rs.updateDouble("salary", 925.0);
            rs.insertRow();
            rs.moveToCurrentRow();
            rs.last();
            rowCount = rs.getRow();
        }

        assertEquals("Row Count after inserting a row", 2, rowCount);
    }

    private Employee populateResultSet(ResultSet rs) throws SQLException {
        Employee employee;
        String name = rs.getString("name");
        Integer empId = rs.getInt("emp_id");
        Double salary = rs.getDouble("salary");
        String position = rs.getString("position");
        employee = new Employee(empId, name, salary, position);
        return employee;
    }

    @Test
    public void givenDbConnectionE_whenRowCount_thenCorrect() throws SQLException {
        int numOfRows = 0;
        try (PreparedStatement pstmt = dbConnection.prepareStatement("select * from employees", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE); ResultSet rs = pstmt.executeQuery()) {
            rs.last();
            numOfRows = rs.getRow();
        }

        assertEquals("Num of rows", rowCount, numOfRows);
    }

    @Test
    public void givenDbConnectionG_whenAbsoluteNavigation_thenCorrect() throws SQLException {
        Employee secondEmployee = null;
        try (PreparedStatement pstmt = dbConnection.prepareStatement("select * from employees", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE); ResultSet rs = pstmt.executeQuery()) {
            rs.absolute(2);
            secondEmployee = populateResultSet(rs);
        }

        assertEquals("Absolute navigation", expectedEmployee2, secondEmployee);
    }

    @Test
    public void givenDbConnectionH_whenLastNavigation_thenCorrect() throws SQLException {
        Employee secondEmployee = null;
        try (PreparedStatement pstmt = dbConnection.prepareStatement("select * from employees", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE); ResultSet rs = pstmt.executeQuery()) {
            rs.last();
            secondEmployee = populateResultSet(rs);
        }

        assertEquals("Using Last", expectedEmployee2, secondEmployee);
    }

    @Test
    public void givenDbConnectionI_whenNavigation_thenCorrect() throws SQLException {
        Employee firstEmployee = null;
        try (PreparedStatement pstmt = dbConnection.prepareStatement("select * from employees", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Employee employee = populateResultSet(rs);
            }
            rs.beforeFirst();
            while (rs.next()) {
                Employee employee = populateResultSet(rs);
            }
            rs.first();
            while (rs.next()) {
                Employee employee = populateResultSet(rs);
            }
            while (rs.previous()) {
                Employee employee = populateResultSet(rs);
            }
            rs.afterLast();
            while (rs.previous()) {
                Employee employee = populateResultSet(rs);
            }
            rs.last();
            while (rs.previous()) {
                firstEmployee = populateResultSet(rs);
            }
        }

        assertEquals("Several Navigation Options", updatedEmployee1, firstEmployee);
    }

    @Test
    public void givenDbConnectionJ_whenClosedCursor_thenCorrect() throws SQLException {
        Employee employee = null;
        dbConnection.setHoldability(ResultSet.CLOSE_CURSORS_AT_COMMIT);
        try (Statement pstmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.CLOSE_CURSORS_AT_COMMIT)) {
            dbConnection.setAutoCommit(false);
            ResultSet rs = pstmt.executeQuery("select * from employees");
            while (rs.next()) {
                if (rs.getString("name")
                    .equalsIgnoreCase("john")) {
                    rs.updateString("position", "Senior Engineer");
                    rs.updateRow();
                    dbConnection.commit();
                    employee = populateResultSet(rs);
                }
            }
            rs.last();
        }

        assertEquals("Update using closed cursor", "Senior Engineer", employee.getPosition());
    }

    @Test
    public void givenDbConnectionK_whenUpdate_thenCorrect() throws SQLException {
        Employee employee = null;
        dbConnection.setHoldability(ResultSet.HOLD_CURSORS_OVER_COMMIT);
        try (Statement pstmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT)) {
            dbConnection.setAutoCommit(false);
            ResultSet rs = pstmt.executeQuery("select * from employees");
            while (rs.next()) {
                if (rs.getString("name")
                    .equalsIgnoreCase("john")) {
                    rs.updateString("name", "John Doe");
                    rs.updateRow();
                    dbConnection.commit();
                    employee = populateResultSet(rs);
                }
            }
            rs.last();
        }

        assertEquals("Update using open cursor", "John Doe", employee.getName());
    }

    @Test
    public void givenDbConnectionM_whenDelete_thenCorrect() throws SQLException {
        int numOfRows = 0;
        try (PreparedStatement pstmt = dbConnection.prepareStatement("select * from employees", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE); ResultSet rs = pstmt.executeQuery()) {
            rs.absolute(2);
            rs.deleteRow();
        }
        try (PreparedStatement pstmt = dbConnection.prepareStatement("select * from employees", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE); ResultSet rs = pstmt.executeQuery()) {
            rs.last();
            numOfRows = rs.getRow();
        }
        assertEquals("Deleted row", 1, numOfRows);
    }

    @Test
    public void givenDbConnectionC_whenUpdate_thenCorrect() throws SQLException {
        Employee employee = null;
        try (PreparedStatement pstmt = dbConnection.prepareStatement("select * from employees", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {

                Assert.assertEquals(1100.0, rs.getDouble("salary"));

                rs.updateDouble("salary", 1200.0);
                rs.updateRow();

                Assert.assertEquals(1200.0, rs.getDouble("salary"));

                String name = rs.getString("name");
                Integer empId = rs.getInt("emp_id");
                Double salary = rs.getDouble("salary");
                String position = rs.getString("position");
                employee = new Employee(empId, name, salary, position);
            }
        }
    }

    @Test
    public void givenDbConnectionC_whenUpdateByIndex_thenCorrect() throws SQLException {
        Employee employee = null;
        try (PreparedStatement pstmt = dbConnection.prepareStatement("select * from employees", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {

                Assert.assertEquals(1000.0, rs.getDouble(4));

                rs.updateDouble(4, 1100.0);
                rs.updateRow();
                Assert.assertEquals(1100.0, rs.getDouble(4));

                String name = rs.getString("name");
                Integer empId = rs.getInt("emp_id");
                Double salary = rs.getDouble("salary");
                String position = rs.getString("position");
                employee = new Employee(empId, name, salary, position);
            }
        }
    }

    @Test
    public void givenDbConnectionE_whenDBMetaInfo_thenCorrect() throws SQLException {
        DatabaseMetaData dbmd = dbConnection.getMetaData();
        boolean supportsTypeForward = dbmd.supportsResultSetType(ResultSet.TYPE_FORWARD_ONLY);
        boolean supportsTypeScrollSensitive = dbmd.supportsResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE);
        boolean supportsTypeScrollInSensitive = dbmd.supportsResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE);
        boolean supportsCloseCursorsAtCommit = dbmd.supportsResultSetHoldability(ResultSet.CLOSE_CURSORS_AT_COMMIT);
        boolean supportsHoldCursorsAtCommit = dbmd.supportsResultSetHoldability(ResultSet.HOLD_CURSORS_OVER_COMMIT);
        boolean concurrency4TypeFwdNConcurReadOnly = dbmd.supportsResultSetConcurrency(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        boolean concurrency4TypeFwdNConcurUpdatable = dbmd.supportsResultSetConcurrency(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
        boolean concurrency4TypeScrollInSensitiveNConcurUpdatable = dbmd.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        boolean concurrency4TypeScrollInSensitiveNConcurReadOnly = dbmd.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        boolean concurrency4TypeScrollSensitiveNConcurUpdatable = dbmd.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        boolean concurrency4TypeScrollSensitiveNConcurReadOnly = dbmd.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        int rsHoldability = dbmd.getResultSetHoldability();

        assertEquals("checking scroll sensitivity and concur updates : ", true, concurrency4TypeScrollInSensitiveNConcurUpdatable);
    }

    @Test
    public void givenDbConnectionF_whenRSMetaInfo_thenCorrect() throws SQLException {
        int columnCount = 0;
        try (PreparedStatement pstmt = dbConnection.prepareStatement("select * from employees", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE); ResultSet rs = pstmt.executeQuery()) {
            ResultSetMetaData metaData = rs.getMetaData();
            columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                String catalogName = metaData.getCatalogName(i);
                String className = metaData.getColumnClassName(i);
                String label = metaData.getColumnLabel(i);
                String name = metaData.getColumnName(i);
                String typeName = metaData.getColumnTypeName(i);
                Integer type = metaData.getColumnType(i);
                String tableName = metaData.getTableName(i);
                String schemaName = metaData.getSchemaName(i);
                boolean isAutoIncrement = metaData.isAutoIncrement(i);
                boolean isCaseSensitive = metaData.isCaseSensitive(i);
                boolean isCurrency = metaData.isCurrency(i);
                boolean isDefiniteWritable = metaData.isDefinitelyWritable(i);
                boolean isReadOnly = metaData.isReadOnly(i);
                boolean isSearchable = metaData.isSearchable(i);
                boolean isReadable = metaData.isReadOnly(i);
                boolean isSigned = metaData.isSigned(i);
                boolean isWritable = metaData.isWritable(i);
                int nullable = metaData.isNullable(i);
            }
        }

        assertEquals("column count", 4, columnCount);
    }

    @Test
    public void givenDbConnectionL_whenFetch_thenCorrect() throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Employee> listOfEmployees = new ArrayList<Employee>();
        try {
            pstmt = dbConnection.prepareStatement("select * from employees", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            pstmt.setFetchSize(1);
            rs = pstmt.executeQuery();
            rs.setFetchSize(1);
            while (rs.next()) {
                Employee employee = populateResultSet(rs);
                listOfEmployees.add(employee);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null)
                rs.close();
            if (pstmt != null)
                pstmt.close();
        }

        assertEquals(2, listOfEmployees.size());
    }

    @AfterClass
    public static void closeConnection() throws SQLException {
        PreparedStatement deleteStmt = dbConnection.prepareStatement("drop table employees", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        deleteStmt.execute();
        dbConnection.close();
    }
}