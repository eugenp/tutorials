package com.baeldung.springdependency;

public class TutorialServiceImpl implements TutorialService {
	
	private Tutorial tutorial = null;
	
	public TutorialServiceImpl(Tutorial tutorial) {
		this.tutorial = tutorial;
	}
	
	@Override
	public Tutorial getTutorial() {
		return tutorial;
	}

	@Override
	public void setTutorial(Tutorial tutorial) {
		this.tutorial = tutorial;
	}
	
}
