package com.baeldung.hibernate.manyToMany;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
    
    private static SessionFactory sessionFactory;
    
    private static SessionFactory buildSessionFactory() {
        try {
            StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder().configure("hibernate-annotation.cfg.xml").build();
            Metadata metadata = new MetadataSources(standardServiceRegistry).getMetadataBuilder().build();
            sessionFactory = metadata.getSessionFactoryBuilder().build();


           return sessionFactory; 
        } catch (Throwable ex) {
            System.err.println("SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
 
    public static SessionFactory getSessionFactory() {
        if(sessionFactory == null) {
            sessionFactory = buildSessionFactory(); 
        }
        return sessionFactory; 

    }


}
