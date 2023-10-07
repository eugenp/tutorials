package com.apache.camel.file.processor;

import com.baeldung.camel.apache.file.FileProcessor;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.awaitility.Awaitility;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


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

    @After
    public void tearDown() {
        cleanFolder(new File(SOURCE_FOLDER));
        cleanFolder(new File(DESTINATION_FOLDER));
    }

    private void cleanFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    file.deleteOnExit();
                }
            }
        }
    }

    @Test
    public void givenJavaDSLRoute_whenCamelStart_thenMoveFolderContent() throws Exception {
        final CamelContext camelContext = new DefaultCamelContext();
        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("file://" + SOURCE_FOLDER + "?delete=true").process(new FileProcessor()).to("file://" + DESTINATION_FOLDER);
            }
        });
        camelContext.start();
        verifyFolderContent();
        camelContext.stop();
    }

    @Test
    public void givenSpringDSLRoute_whenCamelStart_thenMoveFolderContent() throws InterruptedException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("camel-context-test.xml");
        verifyFolderContent();
        applicationContext.close();
    }

    private void verifyFolderContent() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        File destinationFile1 = new File(DESTINATION_FOLDER + "/" + dateFormat.format(date) + "File1.txt");
        File destinationFile2 = new File(DESTINATION_FOLDER + "/" + dateFormat.format(date) + "File2.txt");

        Awaitility.await().atMost(DURATION_MILIS, TimeUnit.MILLISECONDS).untilAsserted(() -> {
            assertThat(destinationFile1.exists()).isTrue();
            assertThat(destinationFile2.exists()).isTrue();
        });
    }
}