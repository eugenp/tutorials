package com.baeldung.tx;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public final class TxIntegrationTest {

    private static final String CONTEXT_CONFIG = "classpath:META-INF/spring/integration/spring-integration-tx-context.xml";

    @Test
    public void whenFileDoesntStartWithFail_thenTxSuccessful() throws InterruptedException, IOException {
        final AbstractApplicationContext context =
                new ClassPathXmlApplicationContext(CONTEXT_CONFIG);

        String fileName = System.getProperty("java.io.tmpdir") + "/tx/test1.txt";
        FileWriter fw = new FileWriter(fileName);
        fw.write("PASSED!");
        fw.close();

        context.registerShutdownHook();
        Thread.sleep(5000);

        File file = new File(fileName + ".PASSED");
        Assert.assertTrue(file.exists());
    }

    @Test
    public void whenFileStartsWithFail_thenTxFailed() {

        String fileName = System.getProperty("java.io.tmpdir") + "/tx/test2.txt";

        try {
            final AbstractApplicationContext context =
                    new ClassPathXmlApplicationContext(CONTEXT_CONFIG);

            FileWriter fw = new FileWriter(fileName);
            fw.write("FAILED!");
            fw.close();

            context.registerShutdownHook();
            Thread.sleep(5000);
        } catch (Exception e) {
            // Exception is expected, do nothing
        }

        File file = new File(fileName + ".FAILED");
        Assert.assertTrue(file.exists());
    }

}
