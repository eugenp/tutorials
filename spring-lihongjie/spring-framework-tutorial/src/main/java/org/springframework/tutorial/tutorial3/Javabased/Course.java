package org.springframework.tutorial.tutorial3.Javabased;

public class Course {

	private Module module;
	
	private String name;

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}
	
	public void getName() {
		System.out.println("Name is course" );
	}
	
}
