package com.baeldung.jdbc;

import static org.junit.Assert.*;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * jdbc在线测试
 */
public class JdbcLiveTest {

    private static final Logger LOG = Logger.getLogger(JdbcLiveTest.class);

    private Connection con;

    /**
     * 连接的mysql的地址为：
     * （1）本机mysql
     *
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Before
    public void setup() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");

        con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/mysqlbook_schema?noAccessToProcedureBodies=true",
                "root",
                "123456"
        );

        Statement stmt = con.createStatement();

        String tableSql = "CREATE TABLE IF NOT EXISTS employees (emp_id int PRIMARY KEY AUTO_INCREMENT, name varchar(30), position varchar(30), salary double)";
        stmt.execute(tableSql);
    }

    /**
     * 测试:插入、查询、同一个事物中修改同一条记录
     * @throws SQLException
     */
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
        updatableResultSet.updateDouble("salary", 2000);
        updatableResultSet.updateString("position", "analyst");
        updatableResultSet.insertRow();

        String updatePositionSql = "UPDATE employees SET position=? WHERE emp_id=?";
        PreparedStatement pstmt = con.prepareStatement(updatePositionSql);
        pstmt.setString(1, "lead developer");
        pstmt.setInt(2, 22);

        String updateSalarySql = "UPDATE employees SET salary=? WHERE emp_id=?";
        PreparedStatement pstmt2 = con.prepareStatement(updateSalarySql);
        pstmt2.setDouble(1, 3000);
        pstmt2.setInt(2, 22);

        boolean autoCommit = con.getAutoCommit();

        try {
            con.setAutoCommit(false);
            pstmt.executeUpdate();
            pstmt2.executeUpdate();
            con.commit();
        } catch (SQLException exc) {
            exc.printStackTrace();
            con.rollback();
        } finally {
            con.setAutoCommit(autoCommit);
        }
    }

    /**
     * 测试：mysql存储过程后续需要学习，已经不会了～～～
     *
     * （1）简单案例
     * @see {http://www.voidcn.com/article/p-qfdreduh-bap.html}
     * @see {http://www.voidcn.com/article/p-udythsml-ra.html} 存储过程的应用案例1
     * @see {http://developer.51cto.com/art/201203/321033.htm} 存储过程的应用案例2
     *
     *
     * (2) 如果表不设置主键时，编写的存储过程如下：
     * CREATE DEFINER=`root`@`localhost` PROCEDURE `insertEmployee`(in emp_id_temp int  ,in name_temp VARCHAR(50) , in position_temp VARCHAR(50)  , in salary_temp double)
     * BEGIN
     * 	insert into employees(emp_id,name,position,salary) values (emp_id_temp,name_temp,position_temp,salary_temp);
     * END
     *
     *（3）如果表设置了主键自动生成时，编写的存储过程如下：(注意，设置表的时候，一定要设置除了主键外的唯一索引键，保证唯一)
     *CREATE DEFINER=`root`@`localhost` PROCEDURE `insertEmployee`(out emp_id_temp int  ,in name_temp VARCHAR(50) , in position_temp VARCHAR(50)  , in salary_temp double)
     * BEGIN
     *     insert into employees(name, position, salary) values (name_temp,position_temp,salary_temp);
     * 	set emp_id_temp = (select emp_id from employees where name = name_temp and position = position_temp and salary = salary_temp);
     * END
     *
     * 注意1：如果测试{@link JdbcLiveTest#whenCallProcedure_thenCorrect()}方法时，再数据库中找不到记录的话，请查看{@link JdbcLiveTest#closeConnection()}方法是否已经被注释掉了。
     *
     */
    @Test
    public void whenCallProcedure_thenCorrect() {
        try {
            String preparedSql = "{call insertEmployee(?,?,?,?)}";
            CallableStatement cstmt = con.prepareCall(preparedSql);
            cstmt.setString(2, "ana");
            cstmt.setString(3, "tester");
            cstmt.setDouble(4, 2000);
            cstmt.registerOutParameter(1, Types.INTEGER);
            cstmt.execute();
            int new_id = cstmt.getInt(1);
            System.out.println("new_id:{}" + new_id);
            assertTrue(new_id > 0);
        }
        catch (SQLException exc) {
            LOG.error("Procedure incorrect or does not exist!"  + exc);
        }
    }

    /**
     * (1)简单案例
     * @see {https://blog.csdn.net/sinat_19351993/article/details/47169789}
     * @see {https://blog.csdn.net/somnl/article/details/50902250}
     *
     * 测试：使用存储过程，mysql如何设置自动增长序列sequence
     */
    @Test
    public void getUerId(){
        try{
            String preparedSql = "{call mysqlbook_schema.pro_nextval(?)}";
            CallableStatement prepareCall = con.prepareCall(preparedSql);
            prepareCall.registerOutParameter(1, Types.INTEGER);
            prepareCall.execute();
            int new_id = prepareCall.getInt(1);
            System.out.println("new_id:{}" + new_id);
            assertTrue(new_id > 0);
        }
        catch (SQLException e){
            LOG.error("Procedure incorrect or does not exist!");
        }
    }

    /**
     * 测试：查询数据库中表名称、表列名
     * @throws SQLException
     */
    @Test
    public void whenReadMetadata_thenCorrect() throws SQLException {

        DatabaseMetaData dbmd = con.getMetaData();
        ResultSet tablesResultSet = dbmd.getTables(null, null, "%", null);
        while (tablesResultSet.next()) {
            LOG.info(tablesResultSet.getString("TABLE_NAME"));
        }
        System.out.println("=====================");

        String selectSql = "SELECT * FROM employees";
        Statement stmt = con.createStatement();
        ResultSet resultSet = stmt.executeQuery(selectSql);
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int nrColumns = rsmd.getColumnCount();
        assertEquals(nrColumns, 4);

        for (int i = 1; i <= nrColumns; i++) {
            try {
                LOG.info(rsmd.getColumnName(i));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
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
