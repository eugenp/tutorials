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
        
         Session newSession = HibernateUtil.getSessionFactory().openSession();
         newSession.beginTransaction();
         //loading the previous object from database
         Employee emp2 = (Employee)newSession.get(Employee.class, new Long(1));        
         System.out.println("Employee Name : " + emp2.getFirstname() + " , " + emp2.getLastname());
         //Check the equality
         if(emp1 == emp2){
        	 System.out.println("Emp1 and Emp2 are equal in '==' comparison");
         }else{
        	 System.out.println("Emp1 and Emp2 are not equal in '==' comparison");        	         	
         }
         
         if(emp1.equals(emp2)){
        	 System.out.println("Emp1 and Emp2 are equal in '.equals()' comparison");
         }else{
        	 System.out.println("Emp1 and Emp2 are not equal in '.equals()' comparison");        	         	
         }
                           
         newSession.getTransaction().commit();
         newSession.close();
	}    
}
