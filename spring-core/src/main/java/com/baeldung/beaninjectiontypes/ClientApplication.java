package com.baeldung.beaninjectiontypes;

public class ClientApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        WeatherUpdate weatherUpdate = context.getBean("weatherUpdate", WeatherUpdate.class);

        System.out.println(weatherUpdate.getUpdate());
        context.close();
    }
}
