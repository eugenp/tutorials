package com.baeldung;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DemoApplication {
   public static void main(String[] args) {
      ApplicationContext context = 
             new ClassPathXmlApplicationContext("MyBean.xml");

      Airplane airCheckList = (Airplane) context.getBean("Airplane");

      airCheckList.aileronCheck();
   }
}

