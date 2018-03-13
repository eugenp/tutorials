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
         session.close(); //Close the session. emp1 will go to detached state.
         
         emp1.setFirstname("Brad"); //detached object updated. hibernate not aware of change
         emp1.setLastname("Pitt");         
		
         Session newSession = HibernateUtil.getSessionFactory().openSession();
         newSession.beginTransaction();
         //loading the previous object from database
         Employee emp2 = (Employee)newSession.get(Employee.class, new Long(1));        
         System.out.println("Employee Name : " + emp2.getFirstname() + " , " + emp2.getLastname());
         //trying to re attach the first object
         newSession.merge(emp1);
         //newSession.update(emp1);
         newSession.getTransaction().commit();
         newSession.close();
	}    
}
