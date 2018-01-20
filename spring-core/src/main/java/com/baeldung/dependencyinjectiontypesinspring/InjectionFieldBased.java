package com.baeldung.dependencyinjectiontypesinspring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InjectionFieldBased {

	@Autowired
	private MyDependency myDependency;
	
	public int getMyDependencyValue(){
		return this.myDependency.getValue();
	}
}
