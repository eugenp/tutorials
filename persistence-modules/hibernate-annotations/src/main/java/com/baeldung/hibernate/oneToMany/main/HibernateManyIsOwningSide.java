package com.baeldung.hibernate.oneToMany.main;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.baeldung.hibernate.oneToMany.config.HibernateAnnotationUtil;
import com.baeldung.hibernate.oneToMany.model.Cart;
import com.baeldung.hibernate.oneToMany.model.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateManyIsOwningSide {

    private static final Logger LOGGER = LoggerFactory.getLogger(HibernateManyIsOwningSide.class);

    public static void main(String[] args) {

        Cart cart = new Cart();
        Cart cart2 = new Cart();

        Item item1 = new Item(cart);
        Item item2 = new Item(cart2);

        Set<Item> itemsSet = new HashSet<>();
        itemsSet.add(item1);
        itemsSet.add(item2);
        cart.setItems(itemsSet);

        // Get Session
        SessionFactory sessionFactory = HibernateAnnotationUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            LOGGER.info("Session created");

            // start transaction
            Transaction tx = session.beginTransaction();

            // Save the Model object
            session.save(cart);
            session.save(cart2);
            session.save(item1);
            session.save(item2);

            // Commit transaction
            tx.commit();

            session = sessionFactory.getCurrentSession();
            tx = session.beginTransaction();

            item1 = session.get(Item.class, 1L);
            item2 = session.get(Item.class, 2L);
            tx.commit();

            LOGGER.info("item1 ID={}, Foreign Key CartOIO ID={}", item1.getId(), item1.getCart().getId());
            LOGGER.info("item2 ID={}, Foreign Key CartOIO ID={}", item2.getId(), item2.getCart().getId());

        } catch (Exception e) {
            LOGGER.error("Exception occurred", e);
        } finally {
            if (!sessionFactory.isClosed()) {
                LOGGER.info("Closing SessionFactory");
                sessionFactory.close();
            }
        }
    }
}
