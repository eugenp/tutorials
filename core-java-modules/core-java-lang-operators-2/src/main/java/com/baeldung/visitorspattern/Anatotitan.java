package com.baeldung.visitorspattern;

public class Anatotitan implements Dino{

	
	String behavior() {
		return "very aggressive";
	}
	
	@Override
	public
	String behavior2(Visitor dinobehave) {
		return dinobehave.visit(this);
	}
	

}
