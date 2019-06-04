package application;

import domain.RequestFacts;

public class ConsoleAdapter {
	
	private RequestFacts requestfacts;
	
	public ConsoleAdapter(RequestFacts requestfacts) {
		
		this.requestfacts = requestfacts;
		
	}
	
	public String askForFacts() {
		
		return requestfacts.askForFacts();
	}

}
