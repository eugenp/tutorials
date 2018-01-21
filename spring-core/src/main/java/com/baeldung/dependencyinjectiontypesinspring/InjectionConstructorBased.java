package com.baeldung.dependencyinjectiontypesinspring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InjectionConstructorBased {

	private MyDependency myDependency;
	
	@Autowired
	public InjectionConstructorBased(MyDependency myDependency) {
		this.myDependency = myDependency;
	}
	
	public int getMyDependencyValue(){
		return this.myDependency.getValue();
	}
}
