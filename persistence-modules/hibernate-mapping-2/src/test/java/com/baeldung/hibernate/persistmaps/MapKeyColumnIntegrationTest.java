package com.baeldung.hibernate.persistmaps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.baeldung.hibernate.Strategy;
import com.baeldung.hibernate.persistmaps.mapkeycolumn.Order;

public class MapKeyColumnIntegrationTest {
    private static SessionFactory sessionFactory;

    private Session session;

    @BeforeClass
    public static void beforeTests() {
        sessionFactory = HibernateUtil.getSessionFactory(Strategy.MAP_KEY_COLUMN_BASED);
    }

    @Before
    public void setUp() {
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    @Test
    public void givenData_whenInsertUsingMapKeyColumn_thenPersistMap() {
        Map<String, Double> itemPriceMap = new HashMap<>();
        itemPriceMap.put("Wrangler Jeans", 150.0);
        itemPriceMap.put("Lee Jeans", 180.0);


        Order order = new Order();
        order.setItemPriceMap(itemPriceMap);

        session.persist(order);
        session.getTransaction().commit();

        assertInsertedData();
    }

    private void assertInsertedData() {
        @SuppressWarnings("unchecked")
        List<Order> orderList = session.createQuery("FROM Order").list();

        assertNotNull(orderList);
        assertEquals(1, orderList.size());

        Order order = orderList.get(0);

        Map<String, Double> itemPriceMap = order.getItemPriceMap();
        assertNotNull(itemPriceMap);
        assertEquals(itemPriceMap.size(), 2);
        assertEquals((Double) 150.0, itemPriceMap.get("Wrangler Jeans"));
        assertEquals((Double) 180.0, itemPriceMap.get("Lee Jeans"));

    }

    @After
    public void tearDown() {
        session.close();
    }

    @AfterClass
    public static void afterTests() {
        sessionFactory.close();
    }
}
