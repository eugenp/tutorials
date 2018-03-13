package com.test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.domain.Department;
import com.domain.Employee;

public class Many2One {

    public static void main(String[] args) {
//		add();
//		get();
//		Employee employ = query(1);
//		System.out.println("deaprt name �� " + employ.getDepart().getName() );
        Department depart = add();
        queryDepart(depart.getId());

    }

    //add
    static Department add() {
        Session session = null;
        Transaction tx = null;
        try {
            Department depart = new Department();
            depart.setName("depart1");

//			Employee employ = new Employee();
//			employ.setDepart(depart);
//			employ.setName("employname");

            Employee employ1 = new Employee();
            employ1.setDepart(depart);
            employ1.setName("employname1");

            Employee employ2 = new Employee();
            employ2.setDepart(depart);
            employ2.setName("employname2");

            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.save(depart);
            session.save(employ1);
            session.save(employ2);

            tx.commit();
            return depart;
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            throw e;
        } finally {
            if (session != null)
                session.close();
        }
    }

    //get
    static Employee get() {
        Session session = null;
        Employee employ = null;
        try {
            session = HibernateUtil.getSession();

            employ = (Employee) session.get(Employee.class, 1);

            return employ;
        } catch (HibernateException e) {

            throw e;
        } finally {
            if (session != null)
                session.close();
        }
    }

    static Department queryDepart(int departId) {
        Session session = null;
        Department depart = null;
        try {
            session = HibernateUtil.getSession();

            depart = (Department) session.get(Department.class, departId);

            System.out.println("" + depart.getEmps().size());
            System.out.println(depart.getEmps());

            Set<Employee> set = new HashSet<Employee>();
            set = depart.getEmps();
            Iterator it = set.iterator();
            while (it.hasNext()) {
                Employee em = new Employee();
                em = (Employee) it.next();
                System.out.println(em.getName());
            }

            return depart;
        } catch (HibernateException e) {

            throw e;
        } finally {
            if (session != null)
                session.close();
        }
    }

    //query
    static Employee query(int empId) {
        Session session = null;
        Employee employ = null;
        try {
            session = HibernateUtil.getSession();

            employ = (Employee) session.get(Employee.class, empId);

            System.out.println(" " + employ.getDepart().getName());
            return employ;
        } catch (HibernateException e) {

            throw e;
        } finally {
            if (session != null)
                session.close();
        }
    }
}
