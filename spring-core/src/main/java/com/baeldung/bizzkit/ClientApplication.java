package com.baeldung.bizzkit;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ClientApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(InjectionConfigurator.class);

        BusinessSetterBean business = context.getBean(BusinessSetterBean.class);
        business.processBusinessInfo(20, "Baeldung LLC");

        BusinessConstructorBean business2 = context.getBean(BusinessConstructorBean.class);
        
        business2.processBusinessInfo(10, "BaeldungWeb LLC");

        context.close();
    }

}
