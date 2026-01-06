package com.baeldung.config;

import javax.sql.DataSource;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class HibernateConfig {

    @Bean
    public SessionFactory sessionFactory(DataSource dataSource) {
        Map<String, Object> settings = new HashMap<>();
        settings.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        settings.put("hibernate.hbm2ddl.auto", "create-drop");
        settings.put("hibernate.show_sql", true);
        settings.put("hibernate.connection.datasource", dataSource);

        StandardServiceRegistry registry =
                new StandardServiceRegistryBuilder()
                        .applySettings(settings)
                        .build();

        return new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory();
    }
}
