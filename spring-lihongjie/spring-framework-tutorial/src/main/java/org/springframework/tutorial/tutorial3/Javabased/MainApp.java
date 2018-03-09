package org.springframework.tutorial.tutorial3.Javabased;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp {

	public static void main(String[] args) {
		//使用 AnnotationConfigApplicationContext 注册配置类
		ApplicationContext ctx = new AnnotationConfigApplicationContext(
				AppContext.class);
		Course course = ctx.getBean(Course.class);
		
		course.getName();
	
//		// 注册AppContext类的另一种方法
//		ApplicationContext context = new AnnotationConfigApplicationContext();
//		((AnnotationConfigApplicationContext) context).register(AppContext.class);
	
		
		
	}

}
