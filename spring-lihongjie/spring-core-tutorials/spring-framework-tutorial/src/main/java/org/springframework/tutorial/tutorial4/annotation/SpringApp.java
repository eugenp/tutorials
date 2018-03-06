package org.springframework.tutorial.tutorial4.annotation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringApp {
	
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(AppConfig.class);
		ctx.refresh();
	}
	
	/**
	 * 在创建Address 实例时输出 city :Chengdu.
	 * 当为singleton时，输出一个 city:Chengdu, 即 只创建一个实例。
	 * 当为prototype时，输出2个 city:Chengdu, 即 创建了2个实例。
	 * 
	 */

}
