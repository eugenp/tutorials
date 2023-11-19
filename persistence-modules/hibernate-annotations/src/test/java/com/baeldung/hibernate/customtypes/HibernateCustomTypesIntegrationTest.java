package com.baeldung.hibernate.customtypes;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.junit.Test;

import jakarta.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hibernate.testing.transaction.TransactionUtil.doInHibernate;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class HibernateCustomTypesIntegrationTest {

    @Test
    public void givenEmployee_whenSavedWithCustomTypes_thenEntityIsSaved() {

        final OfficeEmployee e = new OfficeEmployee();
        e.setDateOfJoining(LocalDate.now());

        PhoneNumber number = new PhoneNumber(1, 222, 8781902);
        e.setEmployeeNumber(number);

        Address empAdd = new Address();
        empAdd.setAddressLine1("Street");
        empAdd.setAddressLine2("Area");
        empAdd.setCity("City");
        empAdd.setCountry("Country");
        empAdd.setZipCode(100);

        e.setEmpAddress(empAdd);

        Salary empSalary = new Salary();
        empSalary.setAmount(1000L);
        empSalary.setCurrency("USD");
        e.setSalary(empSalary);

        doInHibernate(this::sessionFactory, session -> {
            session.save(e);
            boolean contains = session.contains(e);
            assertTrue(contains);
        });

    }

    @Test
    public void givenEmployee_whenCustomTypeInQuery_thenReturnEntity() {

        final OfficeEmployee e = new OfficeEmployee();
        e.setDateOfJoining(LocalDate.now());

        PhoneNumber number = new PhoneNumber(1, 222, 8781902);
        e.setEmployeeNumber(number);

        Address empAdd = new Address();
        empAdd.setAddressLine1("Street");
        empAdd.setAddressLine2("Area");
        empAdd.setCity("City");
        empAdd.setCountry("Country");
        empAdd.setZipCode(100);
        e.setEmpAddress(empAdd);

        Salary empSalary = new Salary();
        empSalary.setAmount(1000L);
        empSalary.setCurrency("USD");
        e.setSalary(empSalary);

        doInHibernate(this::sessionFactory, session -> {
            session.save(e);
            session.flush();
            session.refresh(e);

            TypedQuery<OfficeEmployee> query = session.createQuery("FROM OfficeEmployee OE WHERE OE.empAddress.zipCode = :pinCode", OfficeEmployee.class);
            query.setParameter("pinCode",100);
            final List<OfficeEmployee> resultList = query.getResultList();
            int size = resultList.size();

            assertEquals(1, size);
            assertNotNull(resultList.get(0).getEmployeeNumber());
            assertNotNull(resultList.get(0).getEmpAddress());
            assertNotNull(resultList.get(0).getSalary());
        });

    }

    private SessionFactory sessionFactory() {
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
          .applySettings(getProperties())
          .build();

        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        Metadata metadata = metadataSources
          .addAnnotatedClass(OfficeEmployee.class)
          .getMetadataBuilder()
          .applyBasicType(LocalDateStringType.INSTANCE)
          .build();

        return metadata.buildSessionFactory();
    }

    private static Map<String, Object> getProperties() {
        Map<String, Object> dbSettings = new HashMap<>();
        dbSettings.put(Environment.URL, "jdbc:h2:mem:mydb1;DB_CLOSE_DELAY=-1");
        dbSettings.put(Environment.USER, "sa");
        dbSettings.put(Environment.PASS, "");
        dbSettings.put(Environment.DRIVER, "org.h2.Driver");
        dbSettings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        dbSettings.put(Environment.SHOW_SQL, "true");
        dbSettings.put(Environment.HBM2DDL_AUTO, "create");
        return dbSettings;
    }
}
