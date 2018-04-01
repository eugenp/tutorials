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
 
        
        Employee employee1 = new Employee("Sergey", "Brin");
        Employee employee2 = new Employee("Larry", "Page");
        Employee employee3 = new Employee("Marrisa", "Mayer");
        Employee employee4 = new Employee("Matt", "Cutts");

        employee1.getColleagues().add(employee3);
        employee1.getColleagues().add(employee4);
        employee2.getColleagues().add(employee4);
        employee3.getColleagues().add(employee4);
        employee4.getColleagues().add(employee1);
        employee4.getColleagues().add(employee3);
        

        session.save(employee1);
        session.save(employee2);
        session.save(employee3);
        session.save(employee4);
        
        session.getTransaction().commit();
        session.close();
    }
}