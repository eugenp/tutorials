package com.baeldung.configurationproperties;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.properties.ConfigPropertiesDemoApplication;
import com.baeldung.configurationproperties.Database;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DatabaseConfigPropertiesApp.class)
@TestPropertySource("classpath:database-test.properties")
public class DatabasePropertiesIntegrationTest {

    @Autowired
    @Qualifier("dataSource")
    private Database database;
    
    @Test
    public void testDatabaseProperties() {
        Assert.assertNotNull(database);
        Assert.assertEquals("jdbc:postgresql:/localhost:5432", database.getUrl());
        Assert.assertEquals("foo", database.getUsername());
        Assert.assertEquals("bar", database.getPassword());
    }
}
