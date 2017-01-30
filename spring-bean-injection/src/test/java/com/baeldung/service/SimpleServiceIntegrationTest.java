package com.baeldung.service;

import com.baeldung.SpringBeanInjectionApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBeanInjectionApplication.class)
public class SimpleServiceIntegrationTest {

    @Inject
    private SimpleService simpleService;

    @Inject
    private SimpleService simpleInjectService;

    @Autowired
    private SimpleService simpleAutowiredService;

    @Resource
    private SimpleService simpleResourceService;

    @Inject
    private SimpleServiceTwo simpleServiceTwo;

    @Inject
    private SimpleServiceThree simpleServiceThree;

    @Test
    public void whenCallingGetName_thenWeGetNameOfSimpleServiceClass() {
        String simpleServiceClassName = SimpleService.class.getName();
        String classInjectName = simpleAutowiredService.getName();
        String classAutowiredName = simpleInjectService.getName();
        String classResourceName = simpleResourceService.getName();

        assertThat(classInjectName).isEqualTo(simpleServiceClassName);
        assertThat(classAutowiredName).isEqualTo(simpleServiceClassName);
        assertThat(classResourceName).isEqualTo(simpleServiceClassName);
    }

    @Test
    public void whenCallingGetNameViaSimpleServiceTwo_thenWeGetNameOfSimpleServiceClass(){
        String nameFromsimpleService = simpleService.getName();
        String nameFromSimpleServiceTwo = simpleServiceTwo.getNameViaSimpleService();
        assertThat(nameFromSimpleServiceTwo).isEqualTo(nameFromsimpleService);
    }

    @Test
    public void whenCallingGetNameViaSimpleServiceThree_thenWeGetNameOfSimpleServiceClass(){
        String nameFromsimpleService = simpleService.getName();
        String nameFromSimpleServiceThree = simpleServiceThree.getNameViaSimpleService();
        assertThat(nameFromSimpleServiceThree).isEqualTo(nameFromsimpleService);
    }
}
