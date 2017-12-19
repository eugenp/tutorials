package com.baeldung.springdependency;

public class TutorialConstructorImpl implements TutorialService {

    private Tutorial tutorial = null;
    
    public TutorialConstructorImpl() {
    }
    
    public TutorialConstructorImpl(Tutorial tutorial) {
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
