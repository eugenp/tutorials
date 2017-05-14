package org.baeldung.bean.injection;

import org.baeldung.bean.config.ConstructorBasedInjectionPetConfig;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConstructorBasedBeanInjectionUnitTest {

    @Test
    public void givenJavaConfig_whenUsingConstructorBasedBeanInjection_thenCorrectOwnerName() {

        AnnotationConfigApplicationContext ctx
          = new AnnotationConfigApplicationContext();
        ctx.register(ConstructorBasedInjectionPetConfig.class);
        ctx.refresh();

        Pet pet = ctx.getBean(Pet.class);

        Assert.assertEquals("John Doe", pet.getOwner()
            .getName());
    }

    @Test
    public void givenXMLFile_whenUsingConstructorBasedBeanInjection_thenCorrectOwnerName() {

        ApplicationContext ctx
          = new ClassPathXmlApplicationContext("beanInjectionConstructorBased.xml");

        Pet pet = (Pet) ctx.getBean("pet");

        Assert.assertEquals("John Doe", pet.getOwner()
            .getName());
    }
}
