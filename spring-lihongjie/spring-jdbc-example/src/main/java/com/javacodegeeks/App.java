package com.javacodegeeks;

import com.javacodegeeks.dao.EmployeeDao;
import com.javacodegeeks.dao.JDBCEmployeeDao;
import com.javacodegeeks.domain.Employee;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

//        EmployeeDao employeeDao = (EmployeeDao) context.getBean("employeeDao");
//        Employee employee = new Employee(1, "javacode", 30);
//        employeeDao.insert(employee);
//        Employee employee1 = employeeDao.findById(1);
//        System.out.println(employee1);

        JDBCEmployeeDao jdbcEmployeeDao = (JDBCEmployeeDao) context.getBean("jdbcEmployeeDao");
//        Employee employee2 = new Employee(2, "javacode", 30);
//        jdbcEmployeeDao.insert(employee2);
//        Employee employee3 = jdbcEmployeeDao.findById(2);
//        System.out.print(employee3);
        context.close();
    }

}
