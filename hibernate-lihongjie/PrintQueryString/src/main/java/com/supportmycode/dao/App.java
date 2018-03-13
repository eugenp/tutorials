package com.supportmycode.dao;

import java.sql.Date;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.supportmycode.model.Employee;
import com.supportmycode.persistence.HibernateUtil;


public class App 
{
	final static Logger logger = LoggerFactory.getLogger(App.class);
	
    public static void main( String[] args )
    {
    	
    	 logger.info("Hibernate Example Annotations");
         Session session = HibernateUtil.getSessionFactory().openSession();
         
         session.beginTransaction();
         Employee emp = new Employee("James", "Bond", new Date(System.currentTimeMillis()), "007");
         
         session.save(emp);
         session.getTransaction().commit();
         session.close();
		
	}    
}
