package com.baeldung.optionalreturntype;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistUserExample {
    static String persistenceUnit = "com.baeldung.optionalreturntype";
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnit);

    static EntityManager em = emf.createEntityManager();

    public static void main(String[] args) {
        User user1 = new User();
        user1.setUserId(1l);
        user1.setFirstName("Bael Dung");
        em.persist(user1);

        User user2 = em.find(User.class, 1l);
        System.out.print("User2.firstName:" + user2.getFirstName());
    }
}
