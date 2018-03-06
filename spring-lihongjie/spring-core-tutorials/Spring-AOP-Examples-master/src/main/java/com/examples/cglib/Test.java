package com.examples.cglib;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by lihongjie on 7/4/17.
 * 基于CGLIB代理的经典Spring Aop
 * 注释：产生的代理对象被转换成了目标对象，可以猜测，代理对象继承了目标对象。
 */
public class Test {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-aop-cglib-beans.xml");
        PersonServiceImpl person = (PersonServiceImpl) context.getBean("personProxy"); // 这里使用代理对象
        person.add();

    }
}
