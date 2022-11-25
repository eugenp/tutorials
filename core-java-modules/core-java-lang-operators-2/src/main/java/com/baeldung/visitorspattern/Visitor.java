package com.baeldung.visitorspattern;

public interface Visitor {
	
	String visit(Anatotitan anatotitan);
	String visit(Euraptor euraptor);

}
