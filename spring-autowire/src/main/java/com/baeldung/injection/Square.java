package com.baeldung.injection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Square {

	private SquareProperties props;
	private Color color;

	//Constructor invocation
	public Square(SquareProperties name) {
		this.props = name;
	}

	//setter invocation
	@Autowired
	public void addColor(Color color){
		this.color = color;
	}
	
	public void draw() {
		System.out.println("Square Shape");
		// property populated by constructor injection
		System.out.println("Side: " + props.getSide());
		// property populated by setter injection 
		System.out.println("Color: "+ color);
	}

}
