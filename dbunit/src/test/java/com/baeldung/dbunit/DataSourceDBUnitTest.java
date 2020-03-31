package com.baeldung.dbunit;

import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class DataSourceDBUnitTest extends DataSourceBasedDBTestCase {
    @Override
    protected DataSource getDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL(
                "jdbc:h2:mem:default;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:schema.sql'");
        dataSource.setUser("sa");
        dataSource.setPassword("sa");
        return dataSource;
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(getClass()
                .getClassLoader()
                .getResourceAsStream("data.xml"));
    }

    @Test
    public void testSimpleDataSet() throws SQLException {
        final Connection connection = getDataSource()
                .getConnection();

        final ResultSet rs = connection
                .createStatement()
                .executeQuery("select * from iTEMS where id = 1");

        assertThat(rs.next()).isTrue();
        assertThat(rs.getString("title")).isEqualTo("Grey T-Shirt");
    }
}
