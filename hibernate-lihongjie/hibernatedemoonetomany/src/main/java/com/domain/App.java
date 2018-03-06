package com.domain;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import java.util.*;

/**
 * 一对多单向
 * 级联操作save-update
 */
public class App {
    public static SessionFactory sessionFactory;
    private Long idOfTom;
    private Long idOfTomOrder;
    private Long idOfJack;
    private Long idOfJackOrder;

    static {
        try {
            Configuration config = new Configuration();
            config.configure();
            sessionFactory = config.buildSessionFactory();
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void printOrdersOfCustomer(Long customerId) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Customer customer = (Customer) session.get(Customer.class, customerId);
            printOrders(customer.getOrders());
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

    public void saveCustomerAndOrderWithCascade() {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            Customer customer = new Customer("Tom", new HashSet());
            Order order = new Order();
            order.setOrderNumber("Tom_Order001");

            order.setCustomer(customer);
            customer.getOrders().add(order);

            session.save(customer);
            tx.commit();
            idOfTom = customer.getId();
            idOfTomOrder = order.getId();
            System.out.println("idOfJack : " + idOfTom + " idOfJackOrder : " + idOfTomOrder);
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void associateCustomerAndOrder() {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Customer customer = (Customer) session.load(Customer.class, idOfJack);
            Order order = (Order) session.load(Order.class, idOfJackOrder);
            order.setCustomer(customer);
            customer.getOrders().add(order);
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

    public void saveCustomerAndOrderSeparately() {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            Customer customer = new Customer();
            customer.setName("Jack");

            Order order = new Order();
            order.setOrderNumber("Jack_Order001");

            session.save(customer);
            session.save(order);

            tx.commit();
            idOfJack = customer.getId();
            idOfJackOrder = order.getId();

            System.out.println("idOfJack : " + idOfJack + " idOfJackOrder : " + idOfJackOrder);
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void deleteCustomer(Long customerId) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Customer customer = (Customer) session.load(Customer.class, customerId);
            session.delete(customer);
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

    public void removeOrderFromCustomer(Long customerId) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Customer customer = (Customer) session.load(Customer.class, customerId);
            Order order = (Order) customer.getOrders().iterator().next();

            //解除Customer和Order的关联关系
            customer.getOrders().remove(order);
            order.setCustomer(null);
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

    public void printOrders(Set orders) {
        for (Iterator it = orders.iterator(); it.hasNext(); ) {
            Order order = (Order) it.next();
            System.out.println("OrderNumber of " + order.getCustomer().getName() + " :" + order.getOrderNumber());
        }
    }

    public void saveCustomerAndOrderWithInverse() {
        saveCustomerAndOrderSeparately();
        associateCustomerAndOrder();
    }

    public void test() {

//        saveCustomerAndOrderWithCascade();
        saveCustomerAndOrderWithInverse();
//        printOrdersOfCustomer(idOfTom);
        deleteCustomer(idOfJack);
//        removeOrderFromCustomer(idOfTom);
    }

    public static void main(String args[]) {
        new App().test();
        sessionFactory.close();
    }
}