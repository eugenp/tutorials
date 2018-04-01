package com.springinaction.knights;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class KnightMain {
  public static void main(String[] args) {
    ApplicationContext context = 
        new ClassPathXmlApplicationContext("knights.xml"); //<co id="co_loadKnightContext"/>
    
    Knight knight = (Knight) context.getBean("knight"); //<co id="co_getBeanKnight"/>
    
    knight.embarkOnQuest();//<co id="co_useKnight"/>
  }
}
