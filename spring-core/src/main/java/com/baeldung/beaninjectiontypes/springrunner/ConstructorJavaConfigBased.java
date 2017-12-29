package com.baeldung.beaninjectiontypes.springrunner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.baeldung.beaninjectiontypes.javaconfigbased.ConstructorConfig;
import com.baeldung.beaninjectiontypes.javaconfigbased.constructordomain.Employee;

public class ConstructorJavaConfigBased {
	public static void main(String[] args) {

		Employee employee = getEmployeeFromJavaConfig();

		System.out.println(employee);
	}

	private static Employee getEmployeeFromJavaConfig() {
		ApplicationContext context = new AnnotationConfigApplicationContext(ConstructorConfig.class);

		return context.getBean(Employee.class);
	}

}
