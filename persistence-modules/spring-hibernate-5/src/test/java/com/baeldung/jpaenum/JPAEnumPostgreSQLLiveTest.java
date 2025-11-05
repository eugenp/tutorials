package com.baeldung.jpaenum;

import static org.junit.Assert.assertEquals;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JPAEnumPostgreSQLLiveTest {

    private Session session;

    @Before
    public void setUp() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    @After
    public void tearDown() {
        if (session != null) {
            session.close();
        }
        HibernateUtil.shutdown();
    }


    @Test
    public void givenJavEnum_whenUsingPostgreSQLEnumJdbcType_thenInsertSuccessfully() {
        Transaction transaction = session.beginTransaction();

        CustomerOrder order = new CustomerOrder();
        order.setStatus(OrderStatus.COMPLETED);
        session.save(order);
        transaction.commit();

        // enforce JPA retrieve the data from db.
        session.clear();

        CustomerOrder retrievedOrder = session.get(CustomerOrder.class, order.getId());
        assertEquals(OrderStatus.COMPLETED, retrievedOrder.getStatus());
    }
}
