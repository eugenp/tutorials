package in.codelearn.common;
 
import in.codelearn.model.Employee;
import in.codelearn.persistence.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
 
public class Main {
 
    public static void main(String[] args) {
 
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();
 
        Employee manager1 = new Employee("Chuck", "Norris");
        
        Employee employee1 = new Employee("Sergey", "Brin");
        Employee employee2 = new Employee("Larry", "Page");

        employee1.setManager(manager1);
        employee2.setManager(manager1);
        
        session.save(employee1);
        session.save(employee2);
        
        session.getTransaction().commit();
        session.close();
    }
}