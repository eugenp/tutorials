package com.baeldung.dependencyinjectiontypes.spring;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DependencyInjectionTypesTest {
	
	@Test
    public void givenAutowiredAnnotation_WhenSetOnSetter_ThenDependencyValid() {
        
    	ApplicationContext context = new ClassPathXmlApplicationContext("dependencyinjectiontypes-context.xml");
    	ArticleFormatterWithSetterInjection article = (ArticleFormatterWithSetterInjection) context.getBean("articleFormatterWithSetterInjectionBean");
        
    	String originalText = "This is a text !";
    	String formattedArticle = article.format(originalText);
    	
    	assertTrue(originalText.toUpperCase().equals(formattedArticle));
    }
    
    @Test
    public void givenAutowiredAnnotation_WhenSetOnConstructor_ThenDependencyValid() {
    	
    	ApplicationContext context = new ClassPathXmlApplicationContext("dependencyinjectiontypes-context.xml");
    	ArticleFormatterWithConstructorInjection article = (ArticleFormatterWithConstructorInjection) context.getBean("articleFormatterWithConstructorInjectionBean");
        
    	String originalText = "This is a text !";
    	String formattedArticle = article.format(originalText);
    	
    	assertTrue(originalText.toUpperCase().equals(formattedArticle));
    }

}
