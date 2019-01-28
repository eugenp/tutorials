package com.baeldung.inheritance;

/**
 * 装甲车
 * @author zn.wang
 */
public class ArmoredCar extends Car implements Floatable, Flyable{
    /**
     * 防弹窗户
     */
    private int bulletProofWindows;
    /**
     * 模型
     */
    private String model;

    /**
     * 远程启动汽车
     */
    public void remoteStartCar() {
        // this vehicle can be started by using a remote control
    }

    /**
     * 注册模式
     * @return
     */
    public String registerModel() {
        return model;
    }
    
    public String getAValue() {
        // returns value of model defined in base class Car
        return super.model;
        // return this.model;   // will return value of model defined in ArmoredCar
        // return model;   // will return value of model defined in ArmoredCar
    }
    
    public static String msg() {
    	// return super.msg(); // this won't compile.
    	return "ArmoredCar"; 
    }
    
	@Override
    public void floatOnWater() {
        System.out.println("I can float!");
    }
    
	@Override
    public void fly() {
        System.out.println("I can fly!");
    }
    
    public void aMethod() {
        // System.out.println(duration); // Won't compile
        System.out.println(Floatable.duration); // outputs 10
        System.out.println(Flyable.duration); // outputs 20
    }
    
    
}
