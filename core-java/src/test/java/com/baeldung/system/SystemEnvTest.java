package com.baeldung.system;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class SystemEnvTest {

    @Test
    public void givenEnvVars_whenReadEachVar_thenGetValueinResult() {
        Map<String, String> envVariables = System.getenv(); // access collection

        // access each entry in the collection
        for (HashMap.Entry<String, String> entry: envVariables.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());

            Assert.assertNotNull(entry.getKey());
            Assert.assertNotNull(entry.getValue());
        }

        System.out.println("PATH has value: " + System.getenv("PATH"));

        Assert.assertNotNull(System.getenv("PATH"));
    }
}
