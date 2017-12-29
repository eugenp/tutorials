package com.baeldung.beaninjectiontypes.springrunner;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.baeldung.beaninjectiontypes.config.Config;
import com.baeldung.beaninjectiontypes.setterbased.UserService;

public class SetterBased {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
		UserService app = context.getBean(UserService.class);
		app.processMessage("This is Setter-based bean injection!", "Baeldung", "You@xyz.com");
		context.close();
	}

}
