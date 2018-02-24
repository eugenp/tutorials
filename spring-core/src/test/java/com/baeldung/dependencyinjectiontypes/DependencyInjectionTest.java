package com.baeldung.dependencyinjectiontypes;

import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=com.baeldung.setterdi.Config.class, loader=AnnotationConfigContextLoader.class)
public class DependencyInjectionTest {
	
	@Autowired
	private ApplicationContext appContext;
	
	Logger logger = Logger.getLogger(this.getClass());

   /* @Test
    public void givenAutowiredAnnotation_WhenSetOnSetter_ThenDependencyValid() {
        
    	ApplicationContext context = new ClassPathXmlApplicationContext("dependencyinjectiontypes-context.xml");
    	ArticleWithSetterInjection article = (ArticleWithSetterInjection) context.getBean("articleWithSetterInjectionBean");
        
    	String originalText = "This is a text !";
    	String formattedArticle = article.format(originalText);
    	
    	assertTrue(originalText.toUpperCase().equals(formattedArticle));
    }
    
    @Test
    public void givenAutowiredAnnotation_WhenSetOnConstructor_ThenDependencyValid() {
    	
    	ApplicationContext context = new ClassPathXmlApplicationContext("dependencyinjectiontypes-context.xml");
    	ArticleWithConstructorInjection article = (ArticleWithConstructorInjection) context.getBean("articleWithConstructorInjectionBean");
        
    	String originalText = "This is a text !";
    	String formattedArticle = article.format(originalText);
    	
    	assertTrue(originalText.toUpperCase().equals(formattedArticle));
    }*/
    
    @Test
    public void givenAutowiredAnnotation_OnSetter_ThenDependencyValid() {
    	Student student = (Student) appContext.getBean("student");
    	String teacherFound = student.getTeacher();
    	assertTrue(teacherFound.equals("author"));
    }
    
    @Test
    public void givenAutowiredAnnotation_OnConstructor_ThenDependencyValid() {
    	Student2 student2 = (Student2) appContext.getBean("student2");
    	String teacherFound = student2.getTeacher();
    	assertTrue(teacherFound.equals("author"));
    }
    
}
