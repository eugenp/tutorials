package org.apache.camel.file.processor;

import java.io.File;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@RunWith(JUnit4.class)
public class ContentBasedFileRouterIntegrationTest {

    private static final long DURATION_MILIS = 10000;
    private static final String SOURCE_FOLDER = "src/test/source-folder";
    private static final String DESTINATION_FOLDER_TXT = "src/test/destination-folder-txt";
    private static final String DESTINATION_FOLDER_OTHER = "src/test/destination-folder-other";

    @Before
    public void setUp() throws Exception {
        File sourceFolder = new File(SOURCE_FOLDER);
        File destinationFolderTxt = new File(DESTINATION_FOLDER_TXT);
        File destinationFolderOther = new File(DESTINATION_FOLDER_OTHER);

        cleanFolder(sourceFolder);
        cleanFolder(destinationFolderTxt);
        cleanFolder(destinationFolderOther);

        sourceFolder.mkdirs();
        File file1 = new File(SOURCE_FOLDER + "/File1.txt");
        File file2 = new File(SOURCE_FOLDER + "/File2.csv");
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
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("camel-context-ContentBasedFileRouterTest.xml");
        Thread.sleep(DURATION_MILIS);
        applicationContext.close();

    }
}