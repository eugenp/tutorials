package com.baeldung.hibernate.oneToMany.main;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.baeldung.hibernate.oneToMany.config.HibernateAnnotationUtil;
import com.baeldung.hibernate.oneToMany.model.Cart;
import com.baeldung.hibernate.oneToMany.model.Items;
import com.baeldung.hibernate.oneToMany.model.ItemsOIO;

public class HibernateManyisOwningSide {
    public static void main(String[] args) {

        Cart cart = new Cart();
        Cart cart2 = new Cart();

        Items item1 = new Items(cart);
        Items item2 = new Items(cart2);
        Set<Items> itemsSet = new HashSet<Items>();
        itemsSet.add(item1);
        itemsSet.add(item2);

        cart.setItems(itemsSet);

        
        
        SessionFactory sessionFactory = null;
        Session session = null;
        Transaction tx = null;
        try {
            // Get Session
            sessionFactory = HibernateAnnotationUtil.getSessionFactory();
            session = sessionFactory.getCurrentSession();
            System.out.println("Session created");
            // start transaction
            tx = session.beginTransaction();
            // Save the Model object
            session.save(cart);
            session.save(cart2);
            session.save(item1);
            session.save(item2);
            // Commit transaction
            tx.commit();
            session = sessionFactory.getCurrentSession();
            tx = session.beginTransaction();

            item1 = (Items) session.get(Items.class, new Long(1));
            item2 = (Items) session.get(Items.class, new Long(2));
            tx.commit();
            
         
            System.out.println("item1 ID=" + item1.getId() + ", Foreign Key CartOIO ID=" + item1.getCart()
                .getId());
            System.out.println("item2 ID=" + item2.getId() + ", Foreign Key CartOIO ID=" + item2.getCart()
                .getId());

        } catch (Exception e) {
            System.out.println("Exception occured. " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (!sessionFactory.isClosed()) {
                System.out.println("Closing SessionFactory");
                sessionFactory.close();
            }
        }
    }
}
