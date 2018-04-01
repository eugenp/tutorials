package com.alibaba.dubbo.consumer.web;

import com.alibaba.dubbo.provider.service.DemoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created by lihongjie on 7/10/17.
 *
 *
 */
public class Consumer {

    public static void main(String[] args) throws IOException {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        DemoService demoService = (DemoService) context.getBean("demoService");

        String str = demoService.sayHello();
        System.out.println(str);
        System.in.read();

    }
}
