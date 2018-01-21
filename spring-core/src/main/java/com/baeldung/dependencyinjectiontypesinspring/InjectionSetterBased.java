package com.baeldung.dependencyinjectiontypesinspring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InjectionSetterBased {

	private MyDependency myDependency;
	
	@Autowired
	public void setMyDependency(MyDependency myDependency) {
		this.myDependency = myDependency;
	}
	
	public int getMyDependencyValue(){
		return this.myDependency.getValue();
	}
}
