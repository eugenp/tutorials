package com.supportmycode.dao;

import org.hibernate.Session;

import com.supportmycode.model.Employee;
import com.supportmycode.persistence.HibernateUtil;

public class App 
{
    public static void main( String[] args )
    {
	 System.out.println("Hibernate Example Annotations");
     Session session = HibernateUtil.getSessionFactory().openSession();
     
     session.beginTransaction();
     Employee emp1 = (Employee)session.get(Employee.class, new Long(1));
     System.out.println("Employee Name : " + emp1.getFirstname() + " , " + emp1.getLastname());
     
     //Clear the cache
     session.evict(emp1);
     //session.clear();
     
     //loading the same object in same session scope
     Employee emp2 = (Employee)session.get(Employee.class, new Long(1));        
     System.out.println("Employee Name : " + emp2.getFirstname() + " , " + emp2.getLastname());
   
     session.getTransaction().commit();
     session.close(); //Close the session.   
             
}    
}
