package com.baeldung.hexagonal_architecture.core;

import java.util.Map;
import java.util.Random;

import com.baeldung.hexagonal_architecture.core.ObtainFacts;
import com.baeldung.hexagonal_architecture.core.RequestFacts;

public class FactsReader implements RequestFacts{
	
	private ObtainFacts facts;
	
	public FactsReader(ObtainFacts facts) {
		this.facts = facts;
	}
	
	public String askForFacts() {
		Map<Integer,String> fromFile = facts.getFacts();
		int size = fromFile.size();
		Random rand = new Random();
		int index = rand.nextInt(size);
		
		return fromFile.get(index);
	}
}