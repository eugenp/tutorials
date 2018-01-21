package com.baeldung.dependencyinjectiontypesinspring;

import org.springframework.stereotype.Component;

@Component
public class MyDependency {
	
	private int value;
	
	public MyDependency(int value) {
		this.value = value;
	}
	
	public int getValue(){
		return this.value;
	}
}
