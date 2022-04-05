package com.baeldung.jcommander.helloworld;

import com.beust.jcommander.JCommander;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HelloWorldAppUnitTest {

    @Test
    public void whenJCommanderInvokedWithArgs_thenArgsParsed() {

        HelloWorldArgs jArgs = new HelloWorldArgs();
        JCommander helloCmd = JCommander
          .newBuilder()
          .addObject(jArgs)
          .build();

        // when
        String[] argv = new String[] {
          "--name", "JavaWorld"
        };
        helloCmd.parse(argv);

        // then
        assertEquals("JavaWorld", jArgs.getName());
    }
}
