package com.baeldung.dbunit;

import java.sql.Connection;

import javax.sql.DataSource;

import org.dbunit.Assertion;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.github.mjeanroy.dbunit.core.annotations.DbUnitConnection;
import com.github.mjeanroy.dbunit.core.annotations.DbUnitDataSet;
import com.github.mjeanroy.dbunit.integration.junit4.DbUnitJunitRunner;

@RunWith(DbUnitJunitRunner.class)
@DbUnitDataSet("/data.xml")
@DbUnitConnection(url = "jdbc:h2:mem:default;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:schema.sql'", user = "sa", password = "sa")
public class DBUnitPlusTest {

    private static DataSource getDataSource() {
        DbUnitConnection connectionData = DBUnitPlusTest.class.getAnnotation(DbUnitConnection.class);
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL(connectionData.url());
        dataSource.setUser(connectionData.url());
        dataSource.setPassword(connectionData.password());
        return dataSource;
    }

    @Test public void testEmptySchema() throws Exception {
        ITable expectedTable = new FlatXmlDataSetBuilder().build(getClass().getClassLoader().getResourceAsStream("data.xml"))
                .getTable("USER");
        Connection connection = getDataSource().getConnection();
        ITable actualTable = new DatabaseConnection(connection).createDataSet().getTable("USER");
        Assertion.assertEquals(expectedTable, actualTable);
    }
}
