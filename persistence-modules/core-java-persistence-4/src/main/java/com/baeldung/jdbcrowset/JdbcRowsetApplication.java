package com.baeldung.jdbcrowset;

import java.io.FileOutputStream;
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

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JdbcRowsetApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(JdbcRowsetApplication.class, args);
        Statement stmt;
        try {
            Connection conn = DatabaseConfiguration.geth2Connection();

            String drop = "DROP TABLE IF EXISTS customers, associates;";
            String schema = "CREATE TABLE customers (id INT NOT NULL, name VARCHAR(50) NOT NULL, PRIMARY KEY (id)); ";
            String schemapartb = "CREATE TABLE associates (id INT NOT NULL, name VARCHAR(50) NOT NULL, PRIMARY KEY (id));";

            stmt = conn.createStatement();
            stmt.executeUpdate(drop);
            stmt.executeUpdate(schema);
            stmt.executeUpdate(schemapartb);
            // insert data
            DatabaseConfiguration.initDatabase(stmt);
            // JdbcRowSet Example
            String sql = "SELECT * FROM customers";
            JdbcRowSet jdbcRS = RowSetProvider.newFactory().createJdbcRowSet();
            jdbcRS.setType(ResultSet.TYPE_SCROLL_INSENSITIVE);
            jdbcRS.setCommand(sql);
            jdbcRS.execute();
            jdbcRS.addRowSetListener(new ExampleListener());

            while (jdbcRS.next()) {
                // each call to next, generates a cursorMoved event
                System.out.println("id=" + jdbcRS.getString(1));
                System.out.println("name=" + jdbcRS.getString(2));
            }

            // CachedRowSet Example
            String username = "sa";
            String password = "";
            String url = "jdbc:h2:mem:testdb";
            RowSetFactory aFactory = RowSetProvider.newFactory();
            CachedRowSet crs = aFactory.createCachedRowSet();
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

            // WebRowSet example
            WebRowSet wrs = RowSetProvider.newFactory().createWebRowSet();
            wrs.setUsername(username);
            wrs.setPassword(password);
            wrs.setUrl(url);
            wrs.setCommand(sql);
            wrs.execute();
            FileOutputStream ostream = new FileOutputStream("customers.xml");
            wrs.writeXml(ostream);

            // JoinRowSet example
            CachedRowSet customers = aFactory.createCachedRowSet();
            customers.setUsername(username);
            customers.setPassword(password);
            customers.setUrl(url);
            customers.setCommand(sql);
            customers.execute();

            CachedRowSet associates = aFactory.createCachedRowSet();
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

            // FilteredRowSet example
            RowSetFactory rsf = RowSetProvider.newFactory();
            FilteredRowSet frs = rsf.createFilteredRowSet();
            frs.setCommand("select * from customers");
            frs.execute(conn);
            frs.setFilter(new FilterExample("^[A-C].*"));

            ResultSetMetaData rsmd = frs.getMetaData();
            int columncount = rsmd.getColumnCount();
            while (frs.next()) {
                for (int i = 1; i <= columncount; i++) {
                    System.out.println(rsmd.getColumnLabel(i) + " = " + frs.getObject(i) + " ");
                }
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
