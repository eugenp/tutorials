package domain;

import java.util.Map;
import java.util.Random;

public class FactsReader implements RequestFacts{
	
	private ObtainFacts facts;
	
	public FactsReader(ObtainFacts facts) {
		this.facts = facts;
	}
	
	@Override
	public String askForFacts() {
		Map<Integer,String> fromFile = facts.getFacts();
		int size = fromFile.size();
		Random rand = new Random();
		int index = rand.nextInt(size);
		
		return fromFile.get(index);
	}
}