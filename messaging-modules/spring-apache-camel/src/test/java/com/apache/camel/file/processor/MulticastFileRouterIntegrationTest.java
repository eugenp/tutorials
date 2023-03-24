package com.apache.camel.file.processor;

import java.io.File;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MulticastFileRouterIntegrationTest {

    private static final long DURATION_MILIS = 10000;
    private static final String SOURCE_FOLDER = "src/test/source-folder";
    private static final String DESTINATION_FOLDER_WORLD = "src/test/destination-folder-world";
    private static final String DESTINATION_FOLDER_HELLO = "src/test/destination-folder-hello";

    @Before
    public void setUp() throws Exception {
        File sourceFolder = new File(SOURCE_FOLDER);
        File destinationFolderWorld = new File(DESTINATION_FOLDER_WORLD);
        File destinationFolderHello = new File(DESTINATION_FOLDER_HELLO);

        cleanFolder(sourceFolder);
        cleanFolder(destinationFolderWorld);
        cleanFolder(destinationFolderHello);

        sourceFolder.mkdirs();
        File file1 = new File(SOURCE_FOLDER + "/File1.txt");
        File file2 = new File(SOURCE_FOLDER + "/File2.txt");
        file1.createNewFile();
        file2.createNewFile();
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
    public void routeTest() throws InterruptedException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("camel-context-MulticastFileRouterTest.xml");
        Thread.sleep(DURATION_MILIS);
        applicationContext.close();

    }
}
