package com.baeldung.hibernate.joincolumn;

import com.baeldung.hibernate.HibernateUtil;
import java.io.IOException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class JoinColumnIntegrationTest {

    private Session session;
    private Transaction transaction;

    @Before
    public void setUp() throws IOException {
        session = HibernateUtil.getSessionFactory("hibernate-spatial.properties")
                .openSession();
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

}