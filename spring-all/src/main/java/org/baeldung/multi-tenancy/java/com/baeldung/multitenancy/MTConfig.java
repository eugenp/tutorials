package com.baeldung.multitenancy;

import org.hibernate.tool.hbm2ddl.SchemaExport;
import com.baeldung.multitenancy.implementation;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;



@Configuration
public class MTConfig {

    @Bean
    public SessionFactoryBean sessionFactory(Map<String, DataSource> dataSourceMap, DataSource dataSource){

        SessionFactoryBean sf = new SessionFactoryBean();
        sf.setDataSourceMap(dataSourceMap);
        sf.setDataSource(dataSource);

        try{
            Properties prop = new Properties();
            prop.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
            prop.setProperty("hibernate.show_sql", "true");
            prop.setProperty("hibernate.multiTenancy", "DATABASE");
            prop.setProperty("hibernate.tenant_identifier_resolver", "currentSessionTenantIdentifierResolver");
            prop.setProperty("hibernate.multi_tenant_connection_provider", "simpleMultiTenantConnectionProvider");

            sf = new AnnotationConfiguration().setProperties(prop).configure().buildSessionFactory();
            return sf;

        } catch (Throwable ex) {
            System.err.println("Failed to load the SessionFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }

    }

    @Bean(name = "currentSessionTenantIdentifierResolver")
    @Scope("request", proxyMode = ScopedProxyMode.INTERFACES)
    public CurrentSessionTenantIdentifierResolver currentSessionTenantIdentifierResolver(){

        CurrentSessionTenantIdentifierResolver cstir = new CurrentSessionTenantIdentifierResolver();
        return cstir;

    }

    @Bean(name = "simpleMultiTenantConnectionProvider")
    public SimpleMultiTenantConnectionProvider simpleMultiTenantConnectionProvider(Map<String, DataSource> dataSourceMap){

        SimpleMultiTenantConnectionProvider smtcp = new SimpleMultiTenantConnectionProvider();
        smtcp.setDataSourceMap(dataSourceMap);
        return cstir;

    }




}
