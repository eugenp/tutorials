package com.baeldung.persistent;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.h2db.auto.configuration.AutoConfigurationDemo;

@ActiveProfiles("persistent-on")
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(classes = AutoConfigurationDemo.class)
public class FilesLocationUnitTest {

    @BeforeClass
    public static void beforeClass() {

    }

    @Test(expected = Test.None.class)
    public void whenApplicationStarted_thenEmbeddedDbSubfolderCreated() {
        File subdirectory = new File("src/main/resources/db");
        System.out.println(subdirectory.getAbsolutePath());
        assertTrue(subdirectory.exists());
        assertTrue(subdirectory.isDirectory());
    }

    @Test(expected = Test.None.class)
    public void whenApplicationStarted_thenEmbeddedDbFilesCreated() {
        File dbFile = new File("src/main/resources/db/demodb.mv.db");
        System.out.println(dbFile.getAbsolutePath());

        assertTrue(dbFile.exists());
        assertTrue(dbFile.isFile());
    }

    @AfterClass
    public static void cleanUp() {
        File dbFile = new File("src/main/resources/db/demodb.mv.db");
        dbFile.deleteOnExit();
    }
}
