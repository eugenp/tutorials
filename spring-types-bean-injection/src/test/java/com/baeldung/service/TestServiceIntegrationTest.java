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
@ContextConfiguration(classes = { AppConfig.class })
public class TestServiceIntegrationTest {
    @Autowired
    TestService testService;

    @Test
    public void whenCallingGetTestOne_thenWeGetTestOneString() {
        String resultOne = testService.getTestOne();

        Assert.assertThat(resultOne, is("TestOne"));
    }

    @Test
    public void whenCallingGetTestTwo_thenWeGetTestTwoString() {
        String resultTwo = testService.getTestTwo();

        Assert.assertThat(resultTwo, is("TestTwo"));
    }
}