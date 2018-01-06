package com.baeldung.beaninjection.model;
import org.springframework.beans.factory.config.AbstractFactoryBean;

public class CarFactory extends AbstractFactoryBean<Car> {

    @Override
    public Class<?> getObjectType() {
        return Car.class;
    }

    @Override
    public Car createInstance() throws Exception {
        return new Car();
    }

}
