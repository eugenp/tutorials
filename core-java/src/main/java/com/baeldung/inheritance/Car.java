package com.baeldung.inheritance;

/**
 * 汽车
 * @author zn.wang
 */
public class Car {
	private final int DEFAULT_WHEEL_COUNT = 5;
	private final String DEFAULT_MODEL = "Basic";

    /**
     * 轮子
     */
	protected int wheels;

    /**
     * 模型
     */
	protected String model;
    
    public Car() {
    	this.wheels = DEFAULT_WHEEL_COUNT;
    	this.model = DEFAULT_MODEL;
    }
    
    public Car(int wheels, String model) {
    	this.wheels = wheels;
    	this.model = model;
    }

    /**
     * 检查必不可少的部分，如果ok，则启动。
     */
    public void start() {
        // Check essential parts
        // If okay, start.
    }
    public static int count = 10;
    public static String msg() {
        return "Car";
    }

    @Override
    public String toString() {
    	return model;
    } 
}