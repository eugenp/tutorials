package com.abir.spring;

import org.springframework.stereotype.Service;


public class BeanToBeInjectedImpl implements BeanToBeInjected{

	public void somemethod() {
		System.out.println("This is from injected Bean\n");
	}

}
