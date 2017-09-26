package com.baeldung.org;

import org.springframework.beans.factory.annotation.Autowired;

/*
 * Constructor-based Dependency Injection
 * 
 */

public class JavaContent {
	
	private BeanConent beanConent;
	
	@Autowired
	public JavaContent(BeanConent beanConent){
		System.out.println("Inside JavaContent constructor");
		this.beanConent = beanConent;
	}
	
	public void createBean(){
		beanConent.createBean();
	}
	
	/*
	//a setter method to inject the dependency
	public void setBeanConent(BeanConent beanConent){
		System.out.println("Inside JavaContent constructor");
		this.beanConent = beanConent;
	}
	
	//a getter method to inject the dependency
	public BeanConent getBeanConent(){
		return beanConent;
	}
	
	public void createBean(){
		BeanConent.createBean();
	}
	*/
}
