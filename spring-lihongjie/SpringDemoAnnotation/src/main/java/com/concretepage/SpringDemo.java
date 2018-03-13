package com.concretepage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.concretepage.bean.Employee;
public class SpringDemo {
	public static void main(String[] args)  {
	    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
	    ctx.register(AppConfig.class);
	    ctx.refresh();
		Employee employee = ctx.getBean(Employee.class);
		System.out.println(employee.getAddress().getCity());
		System.out.println(employee.getCompany().getCompName());
    	ctx.close();
	}
} 