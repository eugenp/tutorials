package com.baeldung;

import com.baeldung.hibernate.EmployeeDaoHibernate;
import com.baeldung.jpa.EmployeeDaoJpa;

public class Main {

        public static void main(String[] args) {
                EmployeeDaoJpa empDao = new EmployeeDaoJpa();
                empDao.searchEmployeesInsecure("Trudy' or 1=1 or '1'='1").forEach(e -> System.out.println("1. JPA: " + e));
                empDao.searchEmployeesInsecure("Trudy").forEach(e -> System.out.println("1. JPA: " + e));

                empDao.searchEmployeesSecure1("Tru%").forEach(e -> System.out.println("2. JPA: " + e));

                empDao.searchEmployeesSecure2("%").forEach(e -> System.out.println("3. JPA: " + e));
                empDao.close();

                EmployeeDaoHibernate empDaoHibernate = new EmployeeDaoHibernate();
                empDaoHibernate.searchEmployeesInsecure("Trudy").forEach(e -> System.out.println("1. Hibernate: " + e));

                empDaoHibernate.searchEmployeesSecure1("Tru%").forEach(e -> System.out.println("2. Hibernate: " + e));

                empDaoHibernate.searchEmployeesSecure2("%").forEach(e -> System.out.println("3. Hibernate: " + e));
                empDaoHibernate.close();

        }

}
