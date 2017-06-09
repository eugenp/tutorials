package org.sgitario.spring.di.bean;

public class GasEngine implements Engine {

    @Override
    public void start() {
        System.out.println("Gas");
    }

}
