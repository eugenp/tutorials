package com.baeldung.service;

import com.baeldung.config.AppConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class SuperTestServiceIntegrationTest {
    @Autowired
    SuperTestService superTestService;

    @Test
    public void whenCallingGetSuperTestOne_thenWeGetSuperTestOneString() {
        String resultOne = superTestService.getSuperTestOne();

        Assert.assertThat(resultOne, is("SuperTestOne"));
    }

    @Test
    public void whenCallingGetSuperTestTwo_thenWeGetSuperTestTwoString() {
        String resultTwo = superTestService.getSuperTestTwo();

        Assert.assertThat(resultTwo, is("SuperTestTwo"));
    }
}
