package com.apache.camel.file.processor;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DeadLetterChannelFileRouterIntegrationTest {

    private static final long DURATION_MILIS = 10000;
    private static final String SOURCE_FOLDER = "src/test/source-folder";

    @Before
    public void setUp() throws Exception {
        File sourceFolder = new File(SOURCE_FOLDER);

        cleanFolder(sourceFolder);

        sourceFolder.mkdirs();
        File file = new File(SOURCE_FOLDER + "/File.txt");
        file.createNewFile();
    }

    private void cleanFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    file.delete();
                }
            }
        }
    }

    @Test
    public void routeTest() throws InterruptedException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("camel-context-DeadLetterChannelFileRouter.xml");
        Thread.sleep(DURATION_MILIS);
        applicationContext.close();

    }
}
