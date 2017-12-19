package com.baeldung.springdependency;

public class TutorialSetterImpl implements TutorialService {

    private Tutorial tutorial = null;
    
    public TutorialSetterImpl() {
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
