package com.baeldung.hibernate.multitenancy;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.service.ServiceRegistry;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.baeldung.hibernate.multitenancy.database.TenantIdNames;

public abstract class MultitenancyIntegrationTest {

    public abstract String getPropertyFile();

    @Mock
    private CurrentTenantIdentifierResolver currentTenantIdentifierResolver;

    private SessionFactory sessionFactory;

    @Before
    public void setup() throws IOException {
        MockitoAnnotations.initMocks(this);

        Mockito.when(currentTenantIdentifierResolver.validateExistingCurrentSessions())
            .thenReturn(false);

        Properties properties = getHibernateProperties();
        properties.put(AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER, currentTenantIdentifierResolver);

        sessionFactory = buildSessionFactory(properties);

        initTenant(TenantIdNames.MYDB1);
        initTenant(TenantIdNames.MYDB2);
    }

    protected void initTenant(String tenantId) {
        whenCurrentTenantIs(tenantId);
        createCarTable();
    }

    protected void whenCurrentTenantIs(String tenantId) {
        Mockito.when(currentTenantIdentifierResolver.resolveCurrentTenantIdentifier())
            .thenReturn(tenantId);
    }

    protected void whenAddCar(String brand) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Car car = new Car();
        car.setBrand(brand);
        session.save(car);
        tx.commit();
    }

    protected void thenCarFound(String brand) {
        Session session = sessionFactory.openSession();
        assertNotNull(session.get(Car.class, brand));
    }

    protected void thenCarNotFound(String brand) {
        Session session = sessionFactory.openSession();
        assertNull(session.get(Car.class, brand));
    }

    @SuppressWarnings("deprecation")
    private void createCarTable() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.createSQLQuery("drop table Car if exists")
            .executeUpdate();
        session.createSQLQuery("create table Car (brand varchar(255) primary key)")
            .executeUpdate();
        tx.commit();
    }

    private Properties getHibernateProperties() throws IOException {
        Properties properties = new Properties();
        properties.load(getClass().getResourceAsStream(getPropertyFile()));
        return properties;
    }

    private static SessionFactory buildSessionFactory(Properties properties) {
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(properties)
            .build();
        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        metadataSources.addAnnotatedClass(Car.class);
        return metadataSources.buildMetadata()
            .buildSessionFactory();
    }

}
