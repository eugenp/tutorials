package org.baeldung.examples.olingo2;

import javax.persistence.EntityManager;

public class EntityManagerHolder {
    
    private static ThreadLocal<EntityManager> currentEntityManager = new ThreadLocal<>();
    
    
    public static void setCurrentEntityManager(EntityManager em) {
        currentEntityManager.set(em);
    }
    
    public static EntityManager getCurrentEntityManager() {
        return currentEntityManager.get();
    }

}
