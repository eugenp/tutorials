package com.baeldung.di.types;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringDiTypesTest {

    private AnnotationConfigApplicationContext applicationContext;
    
    @Before
    public void initializeConfig(){
        applicationContext=new AnnotationConfigApplicationContext();
        applicationContext.register(ApplicationConfig.class);
        applicationContext.refresh();
    }
    
    @Test
    public void testing_constructor_based_injection(){
        Assert.assertTrue("Application context is null", applicationContext!=null);
        ConstructorBasedInjectionDemo constructorBasedInjectionDemo = applicationContext.getBean(ConstructorBasedInjectionDemo.class);
        
        Assert.assertTrue("ConstructorBasedInjectionDemo bean is null", constructorBasedInjectionDemo!=null);
        String userHomeFromSys = System.getProperty("user.home");
        String userHome = constructorBasedInjectionDemo.getUserHome();
        
        Assert.assertEquals("User home directory from sys and from bean are different", userHomeFromSys, userHome);
    }
    
    @Test
    public void testing_setter_based_injection(){
        Assert.assertTrue("Application context is null", applicationContext!=null);
        SetterBasedInjectionDemo setterBasedInjectionDemo = applicationContext.getBean(SetterBasedInjectionDemo.class);
        
        Assert.assertTrue("SetterBasedInjectionDemo bean is null", setterBasedInjectionDemo!=null);
        String userHomeFromSys = System.getProperty("user.home");
        String userHome = setterBasedInjectionDemo.getUserHome();
        
        Assert.assertEquals("User home directory from sys and from bean are different", userHomeFromSys, userHome);
    }
    

}
