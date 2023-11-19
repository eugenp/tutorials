package com.apache.camel.file.processor;

import java.io.File;
import java.io.FileWriter;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SplitterFileRouterIntegrationTest {

    private static final long DURATION_MILIS = 10000;
    private static final String SOURCE_FOLDER = "src/test/source-folder";
    private static final String DESTINATION_FOLDER = "src/test/destination-folder";

    @Before
    public void setUp() throws Exception {
        File sourceFolder = new File(SOURCE_FOLDER);
        File destinationFolder = new File(DESTINATION_FOLDER);

        cleanFolder(sourceFolder);
        cleanFolder(destinationFolder);

        sourceFolder.mkdirs();
        File file = new File(SOURCE_FOLDER + "/File.txt");
        FileWriter fileWriter = new FileWriter(file, false);
        fileWriter.write("Hello\nWorld");
        file.createNewFile();
        fileWriter.close();
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
    @Ignore
    public void routeTests() throws InterruptedException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("camel-context-SplitterFileRouter.xml");
        Thread.sleep(DURATION_MILIS);
        applicationContext.close();

    }
}
