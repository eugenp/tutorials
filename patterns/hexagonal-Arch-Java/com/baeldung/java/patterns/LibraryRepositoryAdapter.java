package com.baeldung.java.patterns;

import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * the Adapter class that implements the Respository Port  which acts
 * as the interface to the Database.
 * @author Ganapathy Kumar
 */

@Service
public class LibraryRepositoryAdapter implements LibraryRepositoryPort {
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void subscribe(String emailId) {
        Customer customer = new Customer();
        customer.setEmailAddress(emailId);
        entityManager.persist(customer);
    }

    @Override
    public void unSubscribe(String emailAddress) {
        entityManager.remove(entityManager.find(Customer.class, emailAddress));
    }

}
