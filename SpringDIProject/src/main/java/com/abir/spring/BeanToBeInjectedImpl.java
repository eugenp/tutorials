package com.abir.spring;

import org.springframework.stereotype.Service;

public class BeanToBeInjectedImpl implements BeanToBeInjected {

	public String somemethod() {
		// TODO Auto-generated method stub
		System.out.println("This is from injected Bean\n");
		return "This is from injected Bean";
	}

}
