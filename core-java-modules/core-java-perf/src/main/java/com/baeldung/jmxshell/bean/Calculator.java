package com.baeldung.jmxshell.bean;

public class Calculator implements CalculatorMBean {

    private Integer a = 0;
    private Integer b = 0;

    @Override
    public Integer sum() {
        return a + b;
    }

    @Override
    public Integer getA() {
        return a;
    }

    @Override
    public void setA(Integer a) {
        this.a = a;
    }

    @Override
    public Integer getB() {
        return b;
    }

    @Override
    public void setB(Integer b) {
        this.b = b;
    }
}
