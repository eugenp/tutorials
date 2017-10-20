package com.baeldung.bizzkit;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ClientXmlApplication {
    
    public static void main(String[] args) {
        
        ClassPathXmlApplicationContext setterContext = new ClassPathXmlApplicationContext("BusinessSetterBean.xml");
        ClassPathXmlApplicationContext constructorContext = new ClassPathXmlApplicationContext("BusinessConstructorBean.xml");
        
        BusinessSetterBean business = setterContext.getBean(BusinessSetterBean.class);
        business.processBusinessInfo(20, "Baeldung LLC");

        BusinessConstructorBean business2 = constructorContext.getBean(BusinessConstructorBean.class);
        
        business2.processBusinessInfo(10, "BaeldungWeb LLC");

        setterContext.close();
        constructorContext.close();
}
}
