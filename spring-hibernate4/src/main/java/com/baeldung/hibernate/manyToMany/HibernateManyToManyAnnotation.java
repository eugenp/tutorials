package com.baeldung.hibernate.manyToMany;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class HibernateManyToManyAnnotation {

    public static void main( String[] args )
    {
        SessionFactory sf = null;
        Session session = null;
        Transaction tx = null;
        
        try {
            sf = HibernateUtil.getSessionFactory();
            session = sf.openSession();
            System.out.println("Session created!");
            tx = session.beginTransaction();
     
            
            Project project1 = new Project("IT Project");
            Project project2 = new Project("Networking Project");
            
            Employee employee1 = new Employee("Peter", "Oven");
            Employee employee2 = new Employee("Allan", "Norman");
    
            employee1.getProjects().add(project1);
            employee1.getProjects().add(project2);
            employee2.getProjects().add(project1);
            
            session.save(employee1);
            session.save(employee2);
            
            tx.commit();
            
        } catch(Exception e) {
            System.out.println("An Exception occured " + e);
            e.printStackTrace();
        } finally {
            session.close();
            sf.close();
            System.out.println("Session closed!");
        }

    }
}
