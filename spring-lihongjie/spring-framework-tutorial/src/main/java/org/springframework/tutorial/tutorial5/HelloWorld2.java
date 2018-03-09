package org.springframework.tutorial.tutorial5;

/**
 * Bean Post Processors
 * @author lihongjie
 *
 */
public class HelloWorld2 {
	
	private String message;
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void getMessage() {
		System.out.println("Your Message : " + message);
	}
	
	public void init() {
		System.out.println("Bean is going through init.");
	}
	
	public void destroy() {
		System.out.println("Bean will destroy now.");
	}
}
