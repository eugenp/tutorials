package org.baeldung.dsrouting;

import static org.junit.Assert.assertEquals;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DataSourceRoutingTestConfiguration.class)
public class DataSourceRoutingTests {

    @Autowired
    DataSource routingDatasource;

    @Autowired
    ClientService clientService;

    @Before
    public void setup() {
        final String SQL_ACME_WIDGETS = "insert into client (id, name) values (1, 'ACME WIDGETS')";
        final String SQL_WIDGETS_ARE_US = "insert into client (id, name) values (2, 'WIDGETS ARE US')";
        final String SQL_WIDGET_DEPOT = "insert into client (id, name) values (3, 'WIDGET DEPOT')";

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(routingDatasource);

        ClientDatabaseContextHolder.set(ClientDatabase.ACME_WIDGETS);
        jdbcTemplate.execute(SQL_ACME_WIDGETS);
        ClientDatabaseContextHolder.clear();

        ClientDatabaseContextHolder.set(ClientDatabase.WIDGETS_ARE_US);
        jdbcTemplate.execute(SQL_WIDGETS_ARE_US);
        ClientDatabaseContextHolder.clear();

        ClientDatabaseContextHolder.set(ClientDatabase.WIDGET_DEPOT);
        jdbcTemplate.execute(SQL_WIDGET_DEPOT);
        ClientDatabaseContextHolder.clear();
    }

    @Test
    public void givenClientDbs_whenContextsSwitch_thenRouteToCorrectDatabase() throws Exception {

        // test ACME WIDGETS
        String clientName = clientService.getClientName(ClientDatabase.ACME_WIDGETS);
        assertEquals(clientName, "ACME WIDGETS");

        // test WIDGETS_ARE_US
        clientName = clientService.getClientName(ClientDatabase.WIDGETS_ARE_US);
        assertEquals(clientName, "WIDGETS ARE US");

        // test WIDGET_DEPOT
        clientName = clientService.getClientName(ClientDatabase.WIDGET_DEPOT);
        assertEquals(clientName, "WIDGET DEPOT");
    }
}
