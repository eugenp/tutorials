package com.supportmycode.dao;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.supportmycode.model.Department;
import com.supportmycode.model.Employee;
import com.supportmycode.persistence.HibernateUtil;

public class Main {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {

		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		session.beginTransaction();
		
		System.out.println("*********** Department is fetched ***************");
		Department dept = (Department)session.get(Department.class, new Long(1));
		
		System.out.println("*********** Employee is fetched *************");
		List<Employee> employeeList = dept.getEmployees();		
		for (Iterator<Employee> iterator = employeeList.iterator(); iterator.hasNext();) {
			Employee employee = (Employee) iterator.next();
			System.out.println("Name : " + employee.getFirstname());
			
		}
		session.getTransaction().commit();
		session.close();
	}
}
