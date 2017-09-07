package com.baeldung.beaninjection;

import com.baeldung.beaninjection.domain.Director;
import com.baeldung.beaninjection.domain.Movie;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringRunner {

    public static void main(String[] args) {
        Director kubrick = getDirector();

        System.out.println(kubrick);

        Movie clockworkOrange = getMovie();

        System.out.println(clockworkOrange);

    }

    private static Director getDirector() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beaninjection.xml");
        return context.getBean(Director.class);
    }

    private static Movie getMovie() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beaninjection.xml");
        return context.getBean(Movie.class);
    }
}
