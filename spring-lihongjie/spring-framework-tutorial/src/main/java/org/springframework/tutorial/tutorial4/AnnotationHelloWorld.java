package org.springframework.tutorial.tutorial4;

public class AnnotationHelloWorld {

	private String message;
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void getMessage() {
		
		System.out.println("Your message : " + message);
	}
}
