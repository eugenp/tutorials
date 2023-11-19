package com.baeldung.hibernate.jpabootstrap.config;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;
import jakarta.persistence.EntityManager;
import javax.sql.DataSource;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.spi.PersistenceUnitInfo;
import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.internal.PersistenceUnitInfoDescriptor;

public class JpaEntityManagerFactory {
    
    private final String DB_URL = "jdbc:mysql://databaseurl";
    private final String DB_USER_NAME = "username";
    private final String DB_PASSWORD = "password";
    private final Class[] entityClasses;
    
    public JpaEntityManagerFactory(Class[] entityClasses) {
        this.entityClasses = entityClasses;
    }
            
    public EntityManager getEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }
    
    protected EntityManagerFactory getEntityManagerFactory() {
        PersistenceUnitInfo persistenceUnitInfo = getPersistenceUnitInfo(getClass().getSimpleName());
        Map<String, Object> configuration = new HashMap<>();
        return new EntityManagerFactoryBuilderImpl(new PersistenceUnitInfoDescriptor(persistenceUnitInfo), configuration)
          .build();
    }
    
    protected HibernatePersistenceUnitInfo getPersistenceUnitInfo(String name) {
        return new HibernatePersistenceUnitInfo(name, getEntityClassNames(), getProperties());
    }
    
    protected List<String> getEntityClassNames() {
        return Arrays.asList(getEntities())
          .stream()
          .map(Class::getName)
          .collect(Collectors.toList());
    }
    
    protected Properties getProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.put("hibernate.id.new_generator_mappings", false);
        properties.put("hibernate.connection.datasource", getMysqlDataSource());
        return properties;
    }
    
    protected Class[] getEntities() {
        return entityClasses;
    }
    
    protected DataSource getMysqlDataSource() {
	MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setURL(DB_URL);
	mysqlDataSource.setUser(DB_USER_NAME);
        mysqlDataSource.setPassword(DB_PASSWORD);
	return mysqlDataSource;
    }
}