package com.baeldung.staticdemo;

/**
 * @author zn.wang
 * 使用内部类的方式，实现的单例模式
 */
public class Singleton  {
    private Singleton() {}
    
    private static class SingletonHolder {    
        public static final Singleton instance = new Singleton();
    }    

    public static Singleton getInstance() {    
        return SingletonHolder.instance;    
    }    
}