package com.baeldung.autoconfiguration.annotationprocessor;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AnnotationProcessorApplication.class)
@TestPropertySource("classpath:databaseproperties-test.properties")
public class DatabasePropertiesIntegrationTest {

    @Autowired
    private DatabaseProperties databaseProperties;

    @Test
    public void whenSimplePropertyQueriedThenReturnsPropertyValue() throws Exception {
        Assert.assertEquals("Incorrectly bound Username property", "baeldung", databaseProperties.getUsername());
        Assert.assertEquals("Incorrectly bound Password property", "password", databaseProperties.getPassword());
    }
    
    @Test
    public void whenNestedPropertyQueriedThenReturnsPropertyValue() throws Exception {
    	Assert.assertEquals("Incorrectly bound Server IP nested property", "127.0.0.1", databaseProperties.getServer().getIp());
        Assert.assertEquals("Incorrectly bound Server Port nested property", 3306, databaseProperties.getServer().getPort());
    }   

}
