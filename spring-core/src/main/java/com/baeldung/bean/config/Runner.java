package com.baeldung.bean.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baeldung.bean.Car;
import com.baeldung.bean.CarEngine;
import com.baeldung.bean.Driver;

public class Runner {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean-config.xml");

        CarEngine carEngine = (CarEngine) applicationContext.getBean("carEngine");
        System.out.println(carEngine);

        Driver driver = (Driver) applicationContext.getBean("driver");
        System.out.println(driver);

        Car car = (Car) applicationContext.getBean("car");
        System.out.println(car);
    }

}
