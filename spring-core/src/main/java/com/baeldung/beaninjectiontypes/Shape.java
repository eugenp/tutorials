package com.baeldung.beaninjectiontypes;

import org.springframework.beans.factory.annotation.Autowired;

public class Shape {

	private Graphics graphics;
	private String name;
	
	public Graphics getGraphics() {
		return graphics;
	}
	@Autowired
	public void setGraphics(Graphics graphics) {
		this.graphics = graphics;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return this.name;
	}
	
	public void drawMe(){
		this.graphics.getSurface().drawShape(this);
	}
	
	
}
