package com.baeldung.hibernate.jpabootstrap.application;

import com.baeldung.hibernate.jpabootstrap.config.JpaEntityManagerFactory;
import com.baeldung.hibernate.jpabootstrap.entities.User;
import javax.persistence.EntityManager;

public class Application {
    
    public static void main(String[] args) {
        EntityManager entityManager = getJpaEntityManager();
        User user = entityManager.find(User.class, 1);
        System.out.println(user);
        entityManager.getTransaction().begin();
        user.setName("John");
        user.setEmail("john@domain.com");
        entityManager.merge(user);
        entityManager.getTransaction().commit();
        entityManager.getTransaction().begin();
        entityManager.persist(new User("Monica", "monica@domain.com"));
        entityManager.getTransaction().commit();
 
         // additional CRUD operations
         
    }

    private static class EntityManagerHolder {
        private static final EntityManager ENTITY_MANAGER = new JpaEntityManagerFactory(
          new Class[]{User.class}).getEntityManager();
    }
    
    public static EntityManager getJpaEntityManager() {
        return EntityManagerHolder.ENTITY_MANAGER;
    }
}