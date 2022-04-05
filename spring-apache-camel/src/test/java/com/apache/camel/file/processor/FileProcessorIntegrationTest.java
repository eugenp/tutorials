package com.apache.camel.file.processor;

import java.io.File;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baeldung.camel.file.FileProcessor;


public class FileProcessorIntegrationTest {

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
    public void moveFolderContentJavaDSLTest() throws Exception {
        final CamelContext camelContext = new DefaultCamelContext();
        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("file://" + SOURCE_FOLDER + "?delete=true").process(new FileProcessor()).to("file://" + DESTINATION_FOLDER);
            }
        });
        camelContext.start();
        Thread.sleep(DURATION_MILIS);
        camelContext.stop();
    }

    @Test
    public void moveFolderContentSpringDSLTest() throws InterruptedException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("camel-context-test.xml");
        Thread.sleep(DURATION_MILIS);
        applicationContext.close();

    }
}