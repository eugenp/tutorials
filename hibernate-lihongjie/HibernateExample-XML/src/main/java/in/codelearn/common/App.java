package in.codelearn.common;

import in.codelearn.persistence.HibernateUtil;

import org.hibernate.Session;

public class App 
{
    public static void main( String[] args )
    {
        System.out.println("Hibernate Example XML");
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        session.beginTransaction();
        Employee emp = new Employee();
        
        emp.setEmployeeCode("123");
        emp.setEmployeeName("James Bond");
        
        session.save(emp);
        session.getTransaction().commit();
    }
}
