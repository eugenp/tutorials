package org.baeldung.dsrouting;

import static org.junit.Assert.assertEquals;

import javax.sql.DataSource;

import org.baeldung.dsrouting.ClientDatabase;
import org.baeldung.dsrouting.ClientDatabaseContextHolder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = RoutingTestConfiguration.class)
public class RoutingApplicationTests {

    @Autowired
    DataSource routingDatasource;

    @Before
    public void setup() {
        final String SQL_ACME_WIDGETS = "insert into client (id, name) values (1, 'ACME WIDGETS')";
        final String SQL_WIDGETS_ARE_US = "insert into client (id, name) values (2, 'WIDGETS ARE US')";
        final String SQL_WIDGETS_DEPOT = "insert into client (id, name) values (3, 'WIDGET DEPOT')";

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(routingDatasource);

        ClientDatabaseContextHolder.set(ClientDatabase.ACME_WIDGETS);
        jdbcTemplate.execute(SQL_ACME_WIDGETS);
        ClientDatabaseContextHolder.clear();

        ClientDatabaseContextHolder.set(ClientDatabase.WIDGETS_ARE_US);
        jdbcTemplate.execute(SQL_WIDGETS_ARE_US);
        ClientDatabaseContextHolder.clear();

        ClientDatabaseContextHolder.set(ClientDatabase.WIDGET_DEPOT);
        jdbcTemplate.execute(SQL_WIDGETS_DEPOT);
        ClientDatabaseContextHolder.clear();
    }

    @Test
    public void contextSwitchTest() throws Exception {

        final String SQL_GET_CLIENT_NAME = "select name from client";
        
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(routingDatasource);

        // test default (ACME WIDGETS)
        String clientName = jdbcTemplate.query(SQL_GET_CLIENT_NAME, rowMapper).get(0);
        assertEquals(clientName, "ACME WIDGETS");
 
        // test WIDGETS_ARE_US
        ClientDatabaseContextHolder.set(ClientDatabase.WIDGETS_ARE_US);
        clientName = jdbcTemplate.query(SQL_GET_CLIENT_NAME, rowMapper).get(0);
        assertEquals(clientName, "WIDGETS ARE US");

        // test WIDGETS_ARE_US
        ClientDatabaseContextHolder.set(ClientDatabase.WIDGET_DEPOT);
        clientName = jdbcTemplate.query(SQL_GET_CLIENT_NAME, rowMapper).get(0);
        assertEquals(clientName, "WIDGET DEPOT");
    }

    public static RowMapper<String> rowMapper = (rs, rowNum) -> {
        return rs.getString("name");
    };
}
