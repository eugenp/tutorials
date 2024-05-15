package com.baeldung.hibernate.joincolumn;

import com.baeldung.hibernate.customtypes.LocalDateStringType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class JoinColumnIntegrationTest {

    private Session session;
    private Transaction transaction;

    @Before
    public void setUp() {
        session = sessionFactory().openSession();
        transaction = session.beginTransaction();
    }

    @After
    public void tearDown() {
        transaction.rollback();
        session.close();
    }

    @Test
    public void givenOfficeEntity_setAddress_shouldPersist() {
        Office office = new Office();

        OfficeAddress address = new OfficeAddress();
        address.setZipCode("11-111");
        office.setAddress(address);

        session.save(office);
        session.flush();
        session.clear();
    }

    @Test
    public void givenEmployeeEntity_setEmails_shouldPersist() {
        OfficialEmployee employee = new OfficialEmployee();

        Email email = new Email();
        email.setAddress("example@email.com");
        email.setEmployee(employee);

        session.save(employee);
        session.flush();
        session.clear();
    }

    private SessionFactory sessionFactory() {
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
          .applySettings(getProperties())
          .build();

        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        Metadata metadata = metadataSources
          .addAnnotatedClass(Email.class)
          .addAnnotatedClass(Office.class)
          .addAnnotatedClass(OfficeAddress.class)
          .addAnnotatedClass(OfficialEmployee.class)
          .getMetadataBuilder()
          .applyBasicType(LocalDateStringType.INSTANCE)
          .build();

        return metadata.buildSessionFactory();
    }

    private static Map<String, Object> getProperties() {
        Map<String, Object> dbSettings = new HashMap<>();
        dbSettings.put(Environment.URL, "jdbc:h2:mem:mydbJoinColumn;DB_CLOSE_DELAY=-1");
        dbSettings.put(Environment.USER, "sa");
        dbSettings.put(Environment.PASS, "");
        dbSettings.put(Environment.DRIVER, "org.h2.Driver");
        dbSettings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        dbSettings.put(Environment.SHOW_SQL, "true");
        dbSettings.put(Environment.HBM2DDL_AUTO, "create");
        return dbSettings;
    }

}
