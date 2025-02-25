package com.baeldung.hibernate.persistmaps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.Instant;
import java.util.Date;
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

import com.baeldung.hibernate.HibernateUtil;
import com.baeldung.hibernate.HibernateUtil2;
import com.baeldung.hibernate.Strategy;
import com.baeldung.hibernate.persistmaps.mapkeyjoincolumn.Item;
import com.baeldung.hibernate.persistmaps.mapkeyjoincolumn.Order;
import com.baeldung.hibernate.persistmaps.mapkeyjoincolumn.Seller;

public class MapKeyJoinColumnIntegrationTest {
    private static SessionFactory sessionFactory;

    private Session session;

    @BeforeClass
    public static void beforeTests() {
        sessionFactory = HibernateUtil2.getSessionFactory(Strategy.MAP_KEY_JOIN_COLUMN_BASED);
    }

    @Before
    public void setUp() {
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    @Test
    public void givenData_whenInsertUsingMapKeyJoinColumn_thenPersistMap() {
        Seller seller1 = new Seller();
        seller1.setSellerName("Walmart");

        Item item1 = new Item();
        item1.setItemName("Wrangler Jeans");
        item1.setItemPrice(150.0);
        item1.setItemType(ItemType.JEANS);
        item1.setCreatedOn(Date.from(Instant.ofEpochSecond(1554926573)));
        item1.setSeller(seller1);


        Seller seller2 = new Seller();
        seller2.setSellerName("Amazon");

        Item item2 = new Item();
        item2.setItemName("Armani Tshirts");
        item2.setItemPrice(180.0);
        item2.setItemType(ItemType.TSHIRTS);
        item2.setCreatedOn(Date.from(Instant.ofEpochSecond(1554890573)));
        item2.setSeller(seller2);

        Map<Seller, Item> itemSellerMap = new HashMap<>();
        itemSellerMap.put(seller1, item1);
        itemSellerMap.put(seller2, item2);

        Order order = new Order();
        order.setSellerItemMap(itemSellerMap);


        session.persist(order);
        session.getTransaction().commit();

        assertInsertedData(seller1, item1, seller2, item2);
    }

    private void assertInsertedData(Seller seller1, Item expectedItem1, Seller seller2, Item expectedItem2) {
        @SuppressWarnings("unchecked")
        List<Order> orderList = session.createQuery("FROM Order").list();

        assertNotNull(orderList);
        assertEquals(1, orderList.size());

        Order order = orderList.get(0);

        Map<Seller, Item> sellerItemMap = order.getSellerItemMap();
        assertNotNull(sellerItemMap);
        assertEquals(2, sellerItemMap.size());
        assertEquals(expectedItem1, sellerItemMap.get(seller1));
        assertEquals(expectedItem2, sellerItemMap.get(seller2));

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

