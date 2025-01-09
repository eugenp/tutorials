package com.baeldung.jdbcrowset;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.FilteredRowSet;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.JoinRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import javax.sql.rowset.WebRowSet;

import org.junit.Before;
import org.junit.Test;

public class JdbcRowSetLiveTest {
    Statement stmt = null;
    String username = "sa";
    String password = "";
    String url = "jdbc:h2:mem:testdb";
    String sql = "SELECT * FROM customers";

    @Before
    public void setup() throws Exception {
        Connection conn = DatabaseConfiguration.geth2Connection();

        String drop = "DROP TABLE IF EXISTS customers, associates;";
        String schema = "CREATE TABLE customers (id INT NOT NULL, name VARCHAR(50) NOT NULL, PRIMARY KEY (id)); ";
        String schemapartb = "CREATE TABLE associates (id INT NOT NULL, name VARCHAR(50) NOT NULL, PRIMARY KEY (id));";
        stmt = conn.createStatement();
        stmt.executeUpdate(drop);
        stmt.executeUpdate(schema);
        stmt.executeUpdate(schemapartb);
        DatabaseConfiguration.initDatabase(stmt);

    }

    // JdbcRowSet Example
    @Test
    public void createJdbcRowSet_SelectCustomers_ThenCorrect() throws Exception {

        String sql = "SELECT * FROM customers";
        JdbcRowSet jdbcRS = RowSetProvider.newFactory().createJdbcRowSet();
        jdbcRS.setUrl("jdbc:h2:mem:testdb");
        jdbcRS.setUsername("sa");
        jdbcRS.setPassword("");
        jdbcRS.setType(ResultSet.TYPE_SCROLL_INSENSITIVE);
        jdbcRS.setCommand(sql);
        jdbcRS.execute();
        jdbcRS.addRowSetListener(new ExampleListener());

        while (jdbcRS.next()) {
            // each call to next, generates a cursorMoved event
            System.out.println("id=" + jdbcRS.getString(1));
            System.out.println("name=" + jdbcRS.getString(2));
        }

    }

    // CachedRowSet Example
    @Test
    public void createCachedRowSet_DeleteRecord_ThenCorrect() throws Exception {

        CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();
        crs.setUsername(username);
        crs.setPassword(password);
        crs.setUrl(url);
        crs.setCommand(sql);
        crs.execute();
        crs.addRowSetListener(new ExampleListener());
        while (crs.next()) {
            if (crs.getInt("id") == 1) {
                System.out.println("CRS found customer1 and will remove the record.");
                crs.deleteRow();
                break;
            }
        }
    }

    // WebRowSet example
    @Test
    public void createWebRowSet_SelectCustomers_WritetoXML_ThenCorrect() throws SQLException, IOException {

        WebRowSet wrs = RowSetProvider.newFactory().createWebRowSet();
        wrs.setUsername(username);
        wrs.setPassword(password);
        wrs.setUrl(url);
        wrs.setCommand(sql);
        wrs.execute();
        FileOutputStream ostream = new FileOutputStream("customers.xml");
        wrs.writeXml(ostream);
    }

    // JoinRowSet example
    @Test
    public void createCachedRowSets_DoJoinRowSet_ThenCorrect() throws Exception {

        CachedRowSet customers = RowSetProvider.newFactory().createCachedRowSet();
        customers.setUsername(username);
        customers.setPassword(password);
        customers.setUrl(url);
        customers.setCommand(sql);
        customers.execute();

        CachedRowSet associates = RowSetProvider.newFactory().createCachedRowSet();
        associates.setUsername(username);
        associates.setPassword(password);
        associates.setUrl(url);
        String associatesSQL = "SELECT * FROM associates";
        associates.setCommand(associatesSQL);
        associates.execute();

        JoinRowSet jrs = RowSetProvider.newFactory().createJoinRowSet();
        final String ID = "id";
        final String NAME = "name";
        jrs.addRowSet(customers, ID);
        jrs.addRowSet(associates, ID);
        jrs.last();
        System.out.println("Total rows: " + jrs.getRow());
        jrs.beforeFirst();
        while (jrs.next()) {

            String string1 = jrs.getString(ID);
            String string2 = jrs.getString(NAME);
            System.out.println("ID: " + string1 + ", NAME: " + string2);
        }
    }

    // FilteredRowSet example
    @Test
    public void createFilteredRowSet_filterByRegexExpression_thenCorrect() throws Exception {
        RowSetFactory rsf = RowSetProvider.newFactory();
        FilteredRowSet frs = rsf.createFilteredRowSet();
        frs.setCommand("select * from customers");
        Connection conn = DatabaseConfiguration.geth2Connection();
        frs.execute(conn);
        frs.setFilter(new FilterExample("^[A-C].*"));

        ResultSetMetaData rsmd = frs.getMetaData();
        int columncount = rsmd.getColumnCount();
        while (frs.next()) {
            for (int i = 1; i <= columncount; i++) {
                System.out.println(rsmd.getColumnLabel(i) + " = " + frs.getObject(i) + " ");
            }
        }
    }
}
