package com.alibaba.dubbo.provider.service;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created by lihongjie on 7/10/17.
 *
 *
 */
public class BootStrap {

    public static void main(String[] args) throws IOException {

//        new ClassPathXmlApplicationContext("dubbo-provider.xml");

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        context.start();
        System.in.read(); // 为保证服务一直开着，利用输入流的阻塞来模拟
    }
}
