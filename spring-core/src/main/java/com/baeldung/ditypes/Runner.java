package com.baeldung.ditypes;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.baeldung.ditypes.constructor.PodcastConstructor;
import com.baeldung.ditypes.inner.PodcastField;
import com.baeldung.ditypes.setter.PodcastSetter;

public class Runner {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ConfigurationContext.class);
        
        PodcastConstructor podcastConstructor = context.getBean(PodcastConstructor.class);
        podcastConstructor.info();
        
        PodcastField podcastField = context.getBean(PodcastField.class);
        podcastField.info();
        
        PodcastSetter podcastSetter = context.getBean(PodcastSetter.class);
        podcastSetter.info();
    }
}
