package com.baeldung.setterdi.annotation.dhaval;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = TestConfig.class)
public class SetterDIAnnotationIntegrationTest {

    @Test
    public void givenSetterDI_whenAnnotationConfig_thenExecuteBeginStudy() {

        AbstractApplicationContext context = new AnnotationConfigApplicationContext(TestConfig.class);
        Subject sub = context.getBean(Subject.class);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        sub.beginStudy();

        String expectedOutput = "We are learning Setter DI using Annotations.";

        assertEquals(expectedOutput, outContent.toString());

        context.close();
    }

}
