package com.baeldung.dependencyinjectiontypes;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ArticleFormatter {

    @SuppressWarnings("resource")
    public static void main(String[] args) {    	
        ApplicationContext context = new ClassPathXmlApplicationContext("dependencyinjectiontypes-context.xml");
        ArticleWithSetterInjection article = (ArticleWithSetterInjection) context.getBean("articleWithSetterInjectionBean");
        String formattedArticle = article.format("This is a text !");
        
        System.out.print(formattedArticle);
    }
}
