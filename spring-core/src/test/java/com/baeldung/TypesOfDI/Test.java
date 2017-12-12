package com.baeldung;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.*;

public class Test {
    public static void main(String[] args) {
        Resource res = new ClassPathResource("applicationContext.xml");
        BeanFactory factory = new XmlBeanFactory(res);

        Employee empobj = (Employee) factory.getBean("empBean");
        empobj.show();
        
        Employee empobj2=(Employee)factory.getBean("empBeanSetterInjection");  
        empobj2.show();  
    }
}
