package com.baeldung.service;

import com.baeldung.config.AppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class SimpleServiceIntegrationTest {

    @Autowired
    private SimpleService simpleService;

    @Autowired
    private SimpleServiceTwo simpleServiceTwo;

   @Autowired
    private SimpleServiceThree simpleServiceThree;

    @Test
    public void whenCallingGetName_thenWeGetNameOfSimpleServiceClass() {
        String simpleServiceClassName = SimpleService.class.getName();
        String classInjectName = simpleService.getName();
        assertEquals(classInjectName, simpleServiceClassName);
    }

    @Test
    public void whenCallingGetNameViaSimpleServiceTwo_thenWeGetNameOfSimpleServiceClass(){
        String nameFromsimpleService = simpleService.getName();
        String nameFromSimpleServiceTwo = simpleServiceTwo.getNameViaSimpleService();
        assertEquals(nameFromSimpleServiceTwo, nameFromsimpleService);
    }

    @Test
    public void whenCallingGetNameViaSimpleServiceThree_thenWeGetNameOfSimpleServiceClass(){
        String nameFromsimpleService = simpleService.getName();
        String nameFromSimpleServiceThree = simpleServiceThree.getNameViaSimpleService();
        assertEquals(nameFromSimpleServiceThree, nameFromsimpleService);
    }
}
