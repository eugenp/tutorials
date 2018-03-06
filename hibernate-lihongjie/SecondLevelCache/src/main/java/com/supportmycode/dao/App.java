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
     session.getTransaction().commit();
     session.close(); //Close the session
     
     Session newSession = HibernateUtil.getSessionFactory().openSession();     
     newSession.beginTransaction();
     //loading the same object in same session scope
     Employee emp2 = (Employee)newSession.get(Employee.class, new Long(1));        
     System.out.println("Employee Name : " + emp2.getFirstname() + " , " + emp2.getLastname());
   
     newSession.getTransaction().commit();
     newSession.close(); //Close the session.   
             
}    
}
