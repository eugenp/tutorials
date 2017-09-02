package com.baeldung.constructordi.annotation.dhaval;

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

import com.baeldung.constructordi.annotation.dhaval.Subject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = TestConfig.class)
public class ConstructorDIAnnotationIntegrationTest {

    @Test
    public void givenConstructorDI_whenAnnotationConfig_thenExecuteBeginStudy() {

        AbstractApplicationContext context = new AnnotationConfigApplicationContext(TestConfig.class);
        Subject sub = (Subject) context.getBean("subject");

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        sub.beginStudy();

        String expectedOutput = "We are learning Constructor DI using Annotations.";

        assertEquals(expectedOutput, outContent.toString());

        context.close();
    }

}
