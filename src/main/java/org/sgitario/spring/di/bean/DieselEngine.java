package org.sgitario.spring.di.bean;

public class DieselEngine implements Engine {

    @Override
    public void start() {
        System.out.println("Diesel");
    }

}
