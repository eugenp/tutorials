package com.supportmycode.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.supportmycode.model.Employee;
import com.supportmycode.persistence.HibernateUtil;

public class Main {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {

		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		session.beginTransaction();		
		
		Employee emp1 = new Employee("Nina", "Mayers", "111");		
				
		session.save(emp1);		
		session.getTransaction().commit();
		session.close();
	}
}
