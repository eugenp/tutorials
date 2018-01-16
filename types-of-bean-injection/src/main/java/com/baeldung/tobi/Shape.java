package com.baeldung.tobi;

public class Shape {

	private Graphics graphics;
	private String name;
	
	public Graphics getGraphics() {
		return graphics;
	}
	public void setGraphics(Graphics graphics) {
		this.graphics = graphics;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return this.name;
	}
	
	public void DrawMe(){
		this.graphics.getSurface().drawShape(this);
	}
	
	
}
