package com.baeldung.constructorditwo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baeldung.constructorditwo.Config;
import com.baeldung.constructorditwo.domain.Music;

public class SpringRunner {
	public static void main(String[] args) {
        Music OnTheRadio = getMusicFromXml();

        System.out.println(OnTheRadio);

        OnTheRadio = getMusicFromJavaConfig();

        System.out.println(OnTheRadio);
    }

    private static Music getMusicFromJavaConfig() {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

        return context.getBean(Music.class);
    }

    private static Music getMusicFromXml() {
        ApplicationContext context = new ClassPathXmlApplicationContext("constructorditwo.xml");

        return context.getBean(Music.class);
    }
}
