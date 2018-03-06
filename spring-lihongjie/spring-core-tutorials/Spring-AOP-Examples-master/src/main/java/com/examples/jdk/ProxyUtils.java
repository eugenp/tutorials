package com.examples.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by lihongjie on 7/4/17.
 *
 *
 */
public class ProxyUtils implements InvocationHandler {

    private Object target;

    public ProxyUtils(Object target){
        this.target = target;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("do sth before...");
        method.invoke(target, args);
        System.out.println("do sth after...");
        return null;
    }
}
