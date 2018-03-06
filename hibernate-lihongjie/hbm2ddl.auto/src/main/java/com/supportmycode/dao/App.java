package com.supportmycode.dao;

import java.sql.Date;

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
         Employee emp = new Employee("James", "Bond", new Date(System.currentTimeMillis()), "007");
         
         session.save(emp);
         session.getTransaction().commit();
         session.close();
		
	}    
}
