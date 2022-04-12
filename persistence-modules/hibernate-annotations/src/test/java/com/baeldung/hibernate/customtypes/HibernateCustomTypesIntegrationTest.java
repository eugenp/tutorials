package com.baeldung.hibernate.customtypes;

import com.baeldung.hibernate.HibernateUtil;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.io.IOException;
import java.time.LocalDate;

import static java.util.Collections.singletonList;
import static org.hibernate.testing.transaction.TransactionUtil.doInHibernate;
import static org.junit.Assert.assertEquals;

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
            Assert.assertTrue(contains);
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

            TypedQuery<OfficeEmployee> query = session.createQuery("FROM OfficeEmployee OE WHERE OE.empAddress.zipcode = :pinCode", OfficeEmployee.class);
            query.setParameter("pinCode",100);
            int size = query.getResultList().size();

            assertEquals(1, size);
        });

    }

    private SessionFactory sessionFactory() {
        try {
            return HibernateUtil.getSessionFactory(
              "hibernate-customtypes.properties",
              singletonList(OfficeEmployee.class)
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
