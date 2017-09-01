package com.baeldung.setterdi.dhaval;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SetterDIXmlIntegrationTest {

    private ConfigurableApplicationContext context;

    @Before
    public void initApplicationContext() {
        context = new ClassPathXmlApplicationContext("classpath:/setterdi-test-config.xml");
    }

    @Test
    public void givenSetterDI_whenXmlConfig_thenExecuteBeginStudy() {

        Subject sub = (Subject) context.getBean("subject");

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        sub.beginStudy();

        String expectedOutput = "We are learning Setter DI.";

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
