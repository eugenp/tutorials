package com.baeldung.jdbc;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JdbcLiveTest {

    private static final Logger LOG = Logger.getLogger(JdbcLiveTest.class);

    private Connection con;

    @Before
    public void setup() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/myDb?noAccessToProcedureBodies=true", "user1", "pass");

        try (Statement stmt = con.createStatement()) {

            String tableSql = "CREATE TABLE IF NOT EXISTS employees (emp_id int PRIMARY KEY AUTO_INCREMENT, name varchar(30), position varchar(30), salary double)";
            stmt.execute(tableSql);
        }

    }

    @Test
    public void whenInsertUpdateRecord_thenCorrect() throws SQLException {
        Statement stmt = con.createStatement();

        String insertSql = "INSERT INTO employees(name, position, salary) values ('john', 'developer', 2000)";
        stmt.executeUpdate(insertSql);

        String selectSql = "SELECT * FROM employees";
        ResultSet resultSet = stmt.executeQuery(selectSql);

        List<Employee> employees = new ArrayList<>();

        while (resultSet.next()) {
            Employee emp = new Employee();
            emp.setId(resultSet.getInt("emp_id"));
            emp.setName(resultSet.getString("name"));
            emp.setSalary(resultSet.getDouble("salary"));
            emp.setPosition(resultSet.getString("position"));
            employees.add(emp);
        }

        assertEquals("employees list size incorrect", 1, employees.size());
        assertEquals("name incorrect", "john", employees.iterator().next().getName());
        assertEquals("position incorrect", "developer", employees.iterator().next().getPosition());
        assertEquals("salary incorrect", 2000, employees.iterator().next().getSalary(), 0.1);

        Statement updatableStmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet updatableResultSet = updatableStmt.executeQuery(selectSql);

        updatableResultSet.moveToInsertRow();
        updatableResultSet.updateString("name", "mark");
        updatableResultSet.updateString("position", "analyst");
        updatableResultSet.updateDouble("salary", 2000);
        updatableResultSet.insertRow();

        String updatePositionSql = "UPDATE employees SET position=? WHERE emp_id=?";
        PreparedStatement pstmt = con.prepareStatement(updatePositionSql);
        pstmt.setString(1, "lead developer");
        pstmt.setInt(2, 1);

        String updateSalarySql = "UPDATE employees SET salary=? WHERE emp_id=?";
        PreparedStatement pstmt2 = con.prepareStatement(updateSalarySql);
        pstmt.setDouble(1, 3000);
        pstmt.setInt(2, 1);

        boolean autoCommit = con.getAutoCommit();

        try {
            con.setAutoCommit(false);
            pstmt.executeUpdate();
            pstmt2.executeUpdate();
            con.commit();
        } catch (SQLException exc) {
            con.rollback();
        } finally {
            con.setAutoCommit(autoCommit);
        }
    }

    @Test
    public void whenCallProcedure_thenCorrect() {
        String preparedSql = "{call insertEmployee(?,?,?,?)}";
        try(CallableStatement cstmt = con.prepareCall(preparedSql)) {
            cstmt.setString(2, "ana");
            cstmt.setString(3, "tester");
            cstmt.setDouble(4, 2000);
            cstmt.registerOutParameter(1, Types.INTEGER);
            cstmt.execute();
            int new_id = cstmt.getInt(1);
            assertTrue(new_id > 0);
        } catch (SQLException exc) {
            LOG.error("Procedure incorrect or does not exist!");
        }
    }

    @Test
    public void whenReadMetadata_thenCorrect() throws SQLException {

        DatabaseMetaData dbmd = con.getMetaData();
        try (ResultSet tablesResultSet = dbmd.getTables(null, null, "%", null)) {
            while (tablesResultSet.next()) {
                LOG.info(tablesResultSet.getString("TABLE_NAME"));
            }
        }

        String selectSql = "SELECT * FROM employees";
        Statement stmt = con.createStatement();
        ResultSet resultSet = stmt.executeQuery(selectSql);
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int nrColumns = rsmd.getColumnCount();
        assertEquals(nrColumns, 4);

        IntStream.range(1, nrColumns).forEach(i -> {
            try {
                LOG.info(rsmd.getColumnName(i));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @After
    public void closeConnection() throws SQLException {

        Statement updatableStmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet updatableResultSet = updatableStmt.executeQuery("SELECT * FROM employees");

        while (updatableResultSet.next()) {
            updatableResultSet.deleteRow();
        }

        con.close();
    }

}
