package com.baeldung.hibernate.customtypes;

import com.baeldung.hibernate.HibernateUtil;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;

import static org.hibernate.testing.transaction.TransactionUtil.doInHibernate;

public class HibernateCustomTypesManualTest {

    @Test
    public void givenEmployee_whenSavedWithCustomTypes_thenEntityIsSaved() throws IOException {

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
    public void givenEmployee_whenCustomTypeInQuery_thenReturnEntity() throws IOException {

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

            Query query = session.createQuery("FROM OfficeEmployee OE WHERE OE.empAddress.zipcode = :pinCode");
            query.setParameter("pinCode",100);
            int size = query.list().size();

            Assert.assertEquals(1, size);
        });

    }

    private SessionFactory sessionFactory() {
        try {
            return HibernateUtil.getSessionFactory("hibernate-customtypes.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
