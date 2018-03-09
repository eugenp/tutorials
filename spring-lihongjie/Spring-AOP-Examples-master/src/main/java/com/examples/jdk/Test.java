package com.examples.jdk;

import java.lang.reflect.Proxy;

/**
 * Created by lihongjie on 7/4/17.
 * JDK动态代理
 * 
 *
 */
public class Test {

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        ProxyUtils proxyUtils = new ProxyUtils(userService);
        UserService proxyObject = (UserService) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),UserServiceImpl.class.getInterfaces(), proxyUtils);
        proxyObject.add();
    }
}
