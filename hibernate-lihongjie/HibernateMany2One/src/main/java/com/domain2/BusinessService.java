package com.domain2;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import java.util.*;

/**
 * many to one
 *
 */
public class BusinessService {

    private static SessionFactory sessionFactory;

    static {
        try {
            // Create a configuration based on the properties file we've put
            Configuration config = new Configuration();
            config.configure();
            sessionFactory = config.buildSessionFactory();
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private List findOrdersByCustomer(Customer customer) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            System.out.println("findOrdersByCustomer --------");
            List orders = session.createQuery("from Order as o where o.customer.id=" + customer.getId()).list();
            tx.commit();
            return orders;
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }

    private Customer findCustomer(long customer_id) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            System.out.println("findCustomer --------");
            Customer customer = (Customer) session.get(Customer.class, new Long(customer_id));
            tx.commit();
            return customer;
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }

    private void saveCustomerAndOrderWithCascade() {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            System.out.println("svaeCustomerAndOrderWithCascade --------");
            Customer customer = new Customer("Jack"); // don't need to save customer
            Order order1 = new Order("Jack_Order001", customer);
            Order order2 = new Order("Jack_Order002", customer);

            session.save(order1);
            session.save(order2);

            tx.commit();

        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    private void saveCustomerAndOrder() {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            System.out.println("svaeCustomerAndOrder --------");
            Customer customer = new Customer("Tom");
            session.save(customer);

            Order order1 = new Order("Tom_Order001", customer);
            Order order2 = new Order("Tom_Order002", customer);
            session.save(order1);
            session.save(order2);
            tx.commit();

        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }

    private void printOrders(List orders) {

        System.out.println("printOrders ---------------------------");
        for (Iterator it = orders.iterator(); it.hasNext(); ) {
            Order order = (Order) it.next();
            System.out.println("OrderNumber of " + order.getCustomer().getName() + " :" + order.getOrderNumber());
        }
    }

    private void test() {
        saveCustomerAndOrder();
        saveCustomerAndOrderWithCascade();
        Customer customer = findCustomer(1);
        List orders = findOrdersByCustomer(customer);
        printOrders(orders);
    }

    public static void main(String args[]) {
        new BusinessService().test();
        sessionFactory.close();
    }
}