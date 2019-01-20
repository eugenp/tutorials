package com.baeldung.jdbc;

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

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ResultSetLiveTest {

    private static final Logger logger = Logger.getLogger(ResultSetLiveTest.class);

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
    public void givenDbConnectionA_whenARetreiveByColumnNames_thenCorrect() throws SQLException {
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
    public void givenDbConnectionB_whenBRetreiveByColumnIds_thenCorrect() throws SQLException {
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

        assertEquals("Employee information retreived by column ids.", employee, expectedEmployee1);
    }

    @Test
    public void givenDbConnectionD_whenInsertRow_thenCorrect() throws SQLException {
        int rowCount = 0;
        try (PreparedStatement pstmt = dbConnection.prepareStatement("select * from employees", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE); ResultSet rs = pstmt.executeQuery()) {
            rs.moveToInsertRow();
            rs.updateString("name", "Venkat");
            rs.updateString("position", "DBA");
            rs.updateDouble("salary", 925.0);
            rs.insertRow();
            rs.moveToCurrentRow();
            rs.last();
            rowCount = rs.getRow();
        }

        assertEquals("Row Count after inserting a row", rowCount, 2);
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

        assertEquals("Num of rows", numOfRows, rowCount);
    }

    @Test
    public void givenDbConnectionG_whenAbsoluteNavigation_thenCorrect() throws SQLException {
        Employee secondEmployee = null;
        try (PreparedStatement pstmt = dbConnection.prepareStatement("select * from employees", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE); ResultSet rs = pstmt.executeQuery()) {
            rs.absolute(2);
            secondEmployee = populateResultSet(rs);
        }

        assertEquals("Absolute navigation", secondEmployee, expectedEmployee2);
    }

    @Test
    public void givenDbConnectionH_whenLastNavigation_thenCorrect() throws SQLException {
        Employee secondEmployee = null;
        try (PreparedStatement pstmt = dbConnection.prepareStatement("select * from employees", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE); ResultSet rs = pstmt.executeQuery()) {
            rs.last();
            secondEmployee = populateResultSet(rs);
        }

        assertEquals("Using Last", secondEmployee, expectedEmployee2);
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

        assertEquals("Several Navigation Options", firstEmployee, updatedEmployee1);
    }

    @Test
    public void givenDbConnectionJ_whenClosedCursor_thenCorrect() throws SQLException {
        int numOfRows = 0;
        dbConnection.setHoldability(ResultSet.CLOSE_CURSORS_AT_COMMIT);
        try (Statement pstmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.CLOSE_CURSORS_AT_COMMIT)) {
            dbConnection.setAutoCommit(false);
            pstmt.executeUpdate("INSERT INTO employees (name, salary,position) VALUES ('Chris',2100.0,'Manager')");
            ResultSet rs = pstmt.executeQuery("select * from employees");
            dbConnection.commit();
            while (rs.next()) {
                Employee employee = populateResultSet(rs);
            }
            rs.last();
            numOfRows = rs.getRow();
        }

        assertEquals("Inserted using close cursor after commit", numOfRows, 3);
    }

    @Test
    public void givenDbConnectionK_whenUpdate_thenCorrect() throws SQLException {
        int numOfRows = 0;
        try (Statement pstmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT)) {
            dbConnection.setAutoCommit(false);
            pstmt.executeUpdate("INSERT INTO employees (name, salary,position) VALUES ('Michael',1200.0,'Consultant')");
            ResultSet rs = pstmt.executeQuery("select * from employees");
            dbConnection.commit();
            while (rs.next()) {
                Employee employee = populateResultSet(rs);
            }
            rs.last();
            numOfRows = rs.getRow();
        }

        assertEquals("Inserted using hold cursor after commit", numOfRows, 4);
    }

    @Test
    public void givenDbConnectionL_whenDelete_thenCorrect() throws SQLException {
        int numOfRows = 0;
        try (PreparedStatement pstmt = dbConnection.prepareStatement("select * from employees", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE); ResultSet rs = pstmt.executeQuery()) {
            rs.absolute(3);
            rs.deleteRow();
            rs.last();
            numOfRows = rs.getRow();
        }

        assertEquals("Deleted row", numOfRows, 3);
    }

    @Test
    public void givenDbConnectionC_whenUpdate_thenCorrect() throws SQLException {
        Employee employee = null;
        try (PreparedStatement pstmt = dbConnection.prepareStatement("select * from employees", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                rs.updateDouble("salary", 1100.0);
                rs.updateRow();
                rs.refreshRow();
                String name = rs.getString("name");
                Integer empId = rs.getInt("emp_id");
                Double salary = rs.getDouble("salary");
                String position = rs.getString("position");
                employee = new Employee(empId, name, salary, position);
            }
        }

        assertEquals("Employee information updated successfully.", employee, updatedEmployee1);
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

        assertEquals("checking scroll sensitivity and concur updates : ", concurrency4TypeScrollInSensitiveNConcurUpdatable, true);
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

        assertEquals("column count", columnCount, 4);
    }

    @Test
    public void givenDbConnectionM_whenDelete_thenCorrect() throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Employee> listOfEmployees = new ArrayList<Employee>();
        try {
            pstmt = dbConnection.prepareStatement("select * from employees", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            pstmt.setFetchSize(2);
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
        
        assertEquals(listOfEmployees.size(), 3);
    }

    @AfterClass
    public static void closeConnection() throws SQLException {
        PreparedStatement deleteStmt = dbConnection.prepareStatement("drop table employees", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        deleteStmt.execute();
        dbConnection.close();
    }
}
