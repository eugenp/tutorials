package com.baeldung.config;

import javax.sql.DataSource;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
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
    SessionFactory sessionFactory(DataSource dataSource) {
        StandardServiceRegistry registry =
                new StandardServiceRegistryBuilder()
                        .applySetting("hibernate.connection.datasource", dataSource)
                        .applySetting("hibernate.hbm2ddl.auto", "create-drop")
                        .applySetting("hibernate.show_sql", true)
                        .build();

        MetadataSources sources =
                new MetadataSources(registry);

        return sources.buildMetadata().buildSessionFactory();
    }

}
