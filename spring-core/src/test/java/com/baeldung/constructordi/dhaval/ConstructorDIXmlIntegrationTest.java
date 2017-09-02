package com.baeldung.constructordi.dhaval;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConstructorDIXmlIntegrationTest {

    private ConfigurableApplicationContext context;

    @Before
    public void initApplicationContext() {
        context = new ClassPathXmlApplicationContext("classpath:/constructordi-test-config.xml");
    }

    @Test
    public void givenConstructorDI_whenXmlConfig_thenExecuteBeginStudy() {

        Subject sub = (Subject) context.getBean("subject");

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        sub.beginStudy();

        String expectedOutput = "We are learning Constructor DI.";

        assertEquals(expectedOutput, outContent.toString());

        context.close();
    }

    @After
    public void closeApplicationContext() {
        if (context != null) {
            context.close();
            context = null;
        }
    }
}
