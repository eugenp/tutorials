package com.baeldung.hexagonal.adaptors;

import javax.management.Query;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;

import com.baeldung.hexagonal.core.Customer;
import com.baeldung.hexagonal.ports.IDatabaseOperation;

public class DatabaseOperation implements IDatabaseOperation {

    public boolean updateCustomerData(int customerID, Customer customer) {

        try {
            EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("user-unit");
            EntityManager entitymanager = emfactory.createEntityManager();
            entitymanager.getTransaction().begin();

            Customer cust = entitymanager.find(Customer.class, customerID);

            cust.setPhoneNumber(customer.getPhoneNumber());

            entitymanager.getTransaction().commit();

            entitymanager.close();
            emfactory.close();

            return Boolean.TRUE;
        } catch (Exception e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }

    }
    
    public Customer findCustomer(int customerID) {

        try {
            EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("user-unit");
            EntityManager entitymanager = emfactory.createEntityManager();
            entitymanager.getTransaction().begin();
            Customer cust = entitymanager.find(Customer.class, customerID);
            entitymanager.getTransaction().commit();

            entitymanager.close();
            emfactory.close();

            return cust;
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }

    public boolean AddCustomer(Customer customer) {

        try {
            EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("user-unit");
            EntityManager entitymanager = emfactory.createEntityManager();
            entitymanager.getTransaction().begin();

            entitymanager.persist(customer);
            
            entitymanager.getTransaction().commit();

            entitymanager.close();
            emfactory.close();

            return Boolean.TRUE;
        } catch (Exception e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }

}
