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
public class MegaTestServiceIntegrationTest {
    @Autowired
    MegaTestService megaTestService;

    @Test
    public void whenCallingGetMegaTestOne_thenWeGetMegaTestOneString() {
        String resultOne = megaTestService.getMegaTestOne();

        Assert.assertThat(resultOne, is("MegaTestOne"));
    }

    @Test
    public void whenCallingGetMegaTestTwo_thenWeGetMegaTestTwoString() {
        String resultTwo = megaTestService.getMegaTestTwo();

        Assert.assertThat(resultTwo, is("MegaTestTwo"));
    }
}
