package com.baeldung.jmxshell.bean;

public interface CalculatorMBean {

    Integer sum();

    Integer getA();

    void setA(Integer a);

    Integer getB();

    void setB(Integer b);
}
