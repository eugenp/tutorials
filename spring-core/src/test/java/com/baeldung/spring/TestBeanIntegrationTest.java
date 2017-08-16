/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baeldung.spring;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author morrisonidiasirue
 */
public class TestBeanIntegrationTest {

    @Test
    public void testAssertsions() {

        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        Equipment equipment = (Equipment) context.getBean("equipmentBean");

        Assert.assertEquals("Samsung", equipment.getManufacturer());
        Assert.assertEquals("S6", equipment.getModel());
    }

}
