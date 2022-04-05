package com.baeldung.hibernate.persistmaps;

import com.baeldung.hibernate.HibernateUtil;
import com.baeldung.hibernate.Strategy;
import com.baeldung.hibernate.persistmaps.mapkey.Item;
import com.baeldung.hibernate.persistmaps.mapkeytemporal.Order;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MapKeyTemporalIntegrationTest {
    private static SessionFactory sessionFactory;

    private Session session;

    @BeforeClass
    public static void beforeTests() {
        sessionFactory = HibernateUtil.getSessionFactory(Strategy.MAP_KEY_TEMPORAL_BASED);
    }

    @Before
    public void setUp() {
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    @Test
    public void givenData_whenInsertUsingMapKeyEnumerated_thenPersistMap() {
        Date item1CreatedOn = Date.from(Instant.ofEpochSecond(1554926573));
        Item item1 = new Item();
        item1.setItemName("Wrangler Jeans");
        item1.setItemPrice(150.0);
        item1.setItemType(ItemType.JEANS);
        item1.setCreatedOn(item1CreatedOn);


        Date item2CreatedOn = Date.from(Instant.ofEpochSecond(1554890573));
        Item item2 = new Item();
        item2.setItemName("Armani Tshirts");
        item2.setItemPrice(180.0);
        item2.setItemType(ItemType.TSHIRTS);
        item2.setCreatedOn(item2CreatedOn);

        Map<Date, Item> itemMap = new HashMap<>();
        itemMap.put(item1CreatedOn, item1);
        itemMap.put(item2CreatedOn, item2);

        Order order = new Order();
        order.setItemMap(itemMap);

        session.persist(order);
        session.getTransaction().commit();

        assertInsertedData(item1CreatedOn, item1, item2CreatedOn, item2);
    }

    private void assertInsertedData(Date item1CreatedOn, Item expectedItem1, Date item2CreatedOn, Item expectedItem2) {
        @SuppressWarnings("unchecked")
        List<Order> orderList = session.createQuery("FROM Order").list();

        assertNotNull(orderList);
        assertEquals(1, orderList.size());

        Order order = orderList.get(0);

        Map<Date, Item> itemMap = order.getItemMap();
        assertNotNull(itemMap);
        assertEquals(2, itemMap.size());
        assertEquals(expectedItem1, itemMap.get(item1CreatedOn));
        assertEquals(expectedItem2, itemMap.get(item2CreatedOn));

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
