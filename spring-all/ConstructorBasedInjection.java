package com.abir.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
public class ConstructorBasedInjection {

	private BeanToBeInjected beanToBeInjected;

	@Autowired
	public ConstructorBasedInjection(BeanToBeInjected beanToBeInjected) {
		this.beanToBeInjected = beanToBeInjected;
	}

	public void callingInjectedBeanMethod() {
		System.out.println("Constructor based Injection invoked...");
		beanToBeInjected.somemethod();
	}

}