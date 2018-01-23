package com.baeldung.beaninjectiontypes;

public class Surface {

	public void drawShape(Shape shape){
		System.out.println("Surface is drawing shape : " + shape.getName());
	}
}
