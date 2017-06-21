package org.baeldung.beaninjection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.baeldung.beaninjection.Car;
import org.baeldung.beaninjection.Engine;

public class BeanInjectionSpring {

   public static void main(String[] args) {

      ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

      Car carA = (Car) context.getBean("carA");
      carA.getEngine().start();
      
      Car carB = (Car) context.getBean("carB"); 
      carB.getEngine().start();

   }
}