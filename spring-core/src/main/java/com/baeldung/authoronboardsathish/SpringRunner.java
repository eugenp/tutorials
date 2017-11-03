package com.baeldung.authoronboardsathish;

import com.baeldung.authoronboardsathish.domain.MusicMaker;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;

public class SpringRunner {

    public static void main(String[] args) {
        MusicMaker musicMaker = getMusicFromApplicationContextXMLFile();
        Assert.hasText(musicMaker.toString(), "nameOfMusic='Minor O'");

    }

    private static MusicMaker getMusicFromApplicationContextXMLFile() {
        ApplicationContext context = new ClassPathXmlApplicationContext("authoronboardsathishdi.xml");

        return context.getBean(MusicMaker.class);
    }
}

