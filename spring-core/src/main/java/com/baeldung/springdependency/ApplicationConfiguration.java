package com.baeldung.springdependency;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {
    
    @Bean
    public Tutorial tutorial() {
        return new Tutorial();
    }
    
    @Bean
    public TutorialService tutorialSetter() {
        TutorialService ts = new TutorialSetterImpl();
        ts.setTutorial(tutorial());
        return ts;
    }
    
    @Bean
    public TutorialService tutorialConstructor() {
        TutorialService ts = new TutorialConstructorImpl(tutorial());
        return ts;
    }
    
}
