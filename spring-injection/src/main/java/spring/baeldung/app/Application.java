package spring.baeldung.app;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import spring.baeldung.ConstructorInjectionDemo;
import spring.baeldung.FieldInjectionDemo;
import spring.baeldung.config.AppConfig;

public class Application {

	public static void main(String[] args){
		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
	    /* Field Injection */
		FieldInjectionDemo fieldInjectionDemo = (FieldInjectionDemo) context.getBean("fieldInjectionDemo");
	    fieldInjectionDemo.demo();
	    
	    /*Constructor Injection */
	    ConstructorInjectionDemo constructorInjectionDemo = (ConstructorInjectionDemo) context.getBean("constructorInjectionDemo");
	    constructorInjectionDemo.demo();
	}
}
