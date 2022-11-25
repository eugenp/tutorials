package com.baeldung.visitorspattern;

public class Euraptor  implements Dino{
	
	
	
	String behavior() {
		return "calm";
	}
	

	
	@Override
	public
	String behavior2(Visitor dinobehave) {
		return dinobehave.visit(this);
	}

}
