package com.baeldung.visitorspattern;

public class DinoImpl implements Visitor {

	@Override
	public String visit(Anatotitan anatotitan) {
		return anatotitan.behavior();
	}

	@Override
	public String visit(Euraptor euraptor) {
	    return euraptor.behavior();
	}
	

}
