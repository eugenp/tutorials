package com.baeldung.tobi;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App 
{
    public static void main( String[] args )
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        Shape shape = (Shape)context.getBean("shape");
        shape.setName("Baeldung");
        shape.DrawMe();
    }
}
