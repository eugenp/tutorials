package org.bealdung.bean;

import org.springframework.beans.factory.annotation.Autowired;


public class ChildBean {

	@Autowired
	RootBean rootBean;
	
	public ChildBean(){
        System.out.println("Child Bean");
    }
}
