package org.baeldung.dsrouting;


import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.baeldung.dsrouting.ClientDataSourceRouter;
import org.baeldung.dsrouting.ClientDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
public class RoutingTestConfiguration {


    @Bean("clientDs")
    public DataSource clientDatasource() {
        Map<Object, Object> targetDataSources = new HashMap<>();
        DataSource acmeWidgetsDatasource = acmeWidgetsDatasource();
        DataSource widgetsAreUsDatasource = widgetsAreUsDatasource();
        DataSource widgetsDepotDatasource = widgetsDepotDatasource();
        targetDataSources.put(ClientDatabase.ACME_WIDGETS, acmeWidgetsDatasource);
        targetDataSources.put(ClientDatabase.WIDGETS_ARE_US, widgetsAreUsDatasource);
        targetDataSources.put(ClientDatabase.WIDGET_DEPOT, widgetsDepotDatasource);

        ClientDataSourceRouter clientRoutingDatasource = new ClientDataSourceRouter();
        clientRoutingDatasource.setTargetDataSources(targetDataSources);
        clientRoutingDatasource.setDefaultTargetDataSource(acmeWidgetsDatasource);
        return clientRoutingDatasource;
    }

    private DataSource acmeWidgetsDatasource() {
        EmbeddedDatabaseBuilder dbBuilder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase embeddedDb = dbBuilder.setType(EmbeddedDatabaseType.H2).setName("ACMEWIDGETS").addScript("classpath:db.sql").build();
        return embeddedDb;
    }

    private DataSource widgetsAreUsDatasource() {
        EmbeddedDatabaseBuilder dbBuilder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase embeddedDb = dbBuilder.setType(EmbeddedDatabaseType.H2).setName("WIDGETSAREUS").addScript("classpath:db.sql").build();
        return embeddedDb;
    }

    private DataSource widgetsDepotDatasource() {
        EmbeddedDatabaseBuilder dbBuilder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase embeddedDb = dbBuilder.setType(EmbeddedDatabaseType.H2).setName("WIDGETDEPOT").addScript("classpath:db.sql").build();
        return embeddedDb;
    }
}
