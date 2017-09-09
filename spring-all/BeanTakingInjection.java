package com.abir.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class BeanTakingInjection {

	// @Autowired
	private BeanToBeInjected beantoBeInjected;

	@Autowired
	@Qualifier("somebeanname")
	public void setBeantoBeInjected(BeanToBeInjected beantoBeInjected) {
		this.beantoBeInjected = beantoBeInjected;
	}

	

	public void callingInjectedBeanMethod() {
		System.out.println("Setter/Property Based Injection invoked...");
		beantoBeInjected.somemethod();
	}
}