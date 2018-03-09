package org.springframework.tutorial.tutorial4;

import org.springframework.context.annotation.Scope;

@Scope("singleton")
public class HelloWorld {

	private String message;
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void getMessage() {
		
		System.out.println("Your message : " + message);
	}
}
