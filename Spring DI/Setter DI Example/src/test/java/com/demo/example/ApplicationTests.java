package com.demo.example;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.demo.config.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class ApplicationTests {

    @Autowired
    private Person person;;

    @Test
    public void contextLoads() {

        Assert.assertNotNull(person);
        Assert.assertNotNull(person.getName());
        Assert.assertNotNull(person.getAge());
        Assert.assertNotNull(person);

        Address address = person.getAddress();

        Assert.assertNotNull(address);
        Assert.assertNotNull(address.getCountry());
    }

}
