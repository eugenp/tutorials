package com.baeldung.persistent;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.h2db.auto.configuration.AutoConfigurationDemo;

@ActiveProfiles("persistent-on")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AutoConfigurationDemo.class)
public class FilesLocationUnitTest {

    @Test(expected = Test.None.class)
	public void whenApplicationStarted_thenEmbeddedDbSubfolderCreated() {
        File subdirectory = new File("./subdirectory");
        assertTrue(subdirectory.exists());
        assertTrue(subdirectory.isDirectory());
    }

    @Test(expected = Test.None.class)
    public void whenApplicationStarted_thenEmbeddedDbFilesCreated() {
        File dbFile = new File("./subdirectory/demodb.mv.db");
        assertTrue(dbFile.exists());
        assertTrue(dbFile.isFile());
    }

    @After
    public void cleanUp() {
        File file = new File("subdirectory");
        file.deleteOnExit();
    }
}
