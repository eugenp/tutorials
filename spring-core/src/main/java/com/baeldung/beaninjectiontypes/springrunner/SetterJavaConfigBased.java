package com.baeldung.beaninjectiontypes.springrunner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.baeldung.beaninjectiontypes.javaconfigbased.SetterConfig;
import com.baeldung.beaninjectiontypes.javaconfigbased.setterdomain.Address;

public class SetterJavaConfigBased {
	public static void main(String[] args) {

		Address toyota = getCarFromJavaConfig();

		System.out.println(toyota);

	}

	private static Address getCarFromJavaConfig() {
		ApplicationContext context = new AnnotationConfigApplicationContext(SetterConfig.class);

		return context.getBean(Address.class);
	}

}