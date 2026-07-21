package com.baeldung.hibernate.daterange;

import com.baeldung.hibernate.HibernateUtil;
import com.baeldung.hibernate.daterange.entity.Order;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DateRangeUnitTest {

    private Session session;
    private Transaction transaction;

    @Before
    public void setUp() throws IOException {
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();

        // Clean up
        session.createNativeQuery("delete from orders").executeUpdate();

        // Data for January 2024
        Order o1 = new Order("ORD-001", LocalDateTime.of(2024, 1, 15, 10, 0));
        Order o2 = new Order("ORD-002", LocalDateTime.of(2024, 1, 31, 23, 59, 59));

        // Boundary case: exactly at the start of February (Should be excluded from Jan queries)
        Order o3 = new Order("ORD-003", LocalDateTime.of(2024, 2, 1, 0, 0, 0));

        session.persist(o1);
        session.persist(o2);
        session.persist(o3);

        transaction.commit();
        transaction = session.beginTransaction();
    }

    @After
    public void tearDown() {
        if (transaction != null) {
            transaction.rollback();
        }
        session.close();
    }

    @Test
    public void givenDates_whenUsingHQLComparison_thenJanuaryOrdersFound() {
        LocalDateTime startDate = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 2, 1, 0, 0);

        String hql = "FROM Order o WHERE o.creationDate >= :startDate AND o.creationDate < :endDate";
        Query<Order> query = session.createQuery(hql, Order.class);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);

        List<Order> result = query.getResultList();

        // Should find ORD-001 and ORD-002 (ORD-003 is excluded by the '<' operator)
        assertEquals(2, result.size());
    }

    @Test
    public void givenDates_whenUsingCriteriaAPI_thenJanuaryOrdersFound() {
        LocalDateTime startDate = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 2, 1, 0, 0);

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> root = cq.from(Order.class);

        Predicate startPredicate = cb.greaterThanOrEqualTo(root.get("creationDate"), startDate);
        Predicate endPredicate = cb.lessThan(root.get("creationDate"), endDate);

        cq.select(root).where(cb.and(startPredicate, endPredicate));

        List<Order> result = session.createQuery(cq).getResultList();

        assertEquals(2, result.size());
    }

    @Test
    public void givenDates_whenUsingNativeSQL_thenJanuaryOrdersFound() {
        LocalDateTime startDate = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 2, 1, 0, 0);

        // Native SQL uses the table/column names directly
        String sql = "SELECT * FROM orders WHERE creationDate >= :startDate AND creationDate < :endDate";
        List<Order> result = session.createNativeQuery(sql, Order.class)
          .setParameter("startDate", startDate)
          .setParameter("endDate", endDate)
          .getResultList();

        assertEquals(2, result.size());
    }
}