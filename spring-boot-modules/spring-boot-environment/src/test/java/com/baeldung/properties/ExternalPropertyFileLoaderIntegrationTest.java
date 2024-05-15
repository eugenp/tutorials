package com.baeldung.properties;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ExternalPropertyFileLoader.class)
public class ExternalPropertyFileLoaderIntegrationTest {

    @Autowired
    ConfProperties props;

    @Test
    public void whenExternalisedPropertiesLoaded_thenReadValues() throws IOException {
        assertEquals("jdbc:postgresql://localhost:5432/", props.getUrl());
        assertEquals("admin", props.getUsername());
        assertEquals("root", props.getPassword());
    }

}
