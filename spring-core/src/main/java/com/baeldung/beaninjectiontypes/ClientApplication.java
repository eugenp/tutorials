package com.baeldung.beaninjectiontypes;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ClientApplication {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationConfiguration.xml");

        WeatherUpdate weatherUpdate = context.getBean("localWeatherUpdate", WeatherUpdate.class);

        System.out.println(weatherUpdate.getUpdate());

        context.close();
    }
}