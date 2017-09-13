package com.abir.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class BeanTakingInjection {

	// @Autowired
	private BeanToBeInjected beantoBeInjected;

	public BeanToBeInjected getBeantoBeInjected() {
		return beantoBeInjected;
	}

	public void setBeantoBeInjected(BeanToBeInjected beantoBeInjected) {
		this.beantoBeInjected = beantoBeInjected;
	}

	public String callingInjectedBeanMethod() {
		System.out.println("Setter/Property Based Injection invoked...");
		return beantoBeInjected.somemethod();
	}
}