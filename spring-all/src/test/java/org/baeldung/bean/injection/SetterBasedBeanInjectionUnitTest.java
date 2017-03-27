package org.baeldung.bean.injection;

import org.baeldung.bean.config.SetterBasedInjectionPetConfig;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SetterBasedBeanInjectionUnitTest {

    @Test
    public void givenJavaConfig_whenUsingSetterBasedBeanInjection_thenCorrectOwnerName() {

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(SetterBasedInjectionPetConfig.class);
        ctx.refresh();

        Pet pet = ctx.getBean(Pet.class);

        Assert.assertEquals("John Doe", pet.getOwner()
            .getName());
    }

    @Test
    public void givenXmlFile_whenUsingSetterBasedBeanInjection_thenCorrectOwnerName() {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("beanInjectionSetterBased.xml");

        Pet pet = (Pet) ctx.getBean("pet");

        Assert.assertEquals("John Doe", pet.getOwner()
            .getName());
    }
}
