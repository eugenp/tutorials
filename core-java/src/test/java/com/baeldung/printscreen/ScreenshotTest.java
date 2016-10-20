package com.baeldung.printscreen;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;


public class ScreenshotTest {

    private Screenshot screenshot;
    private String filePath;
    private String fileName;
    private String fileType;
    private File file;

    @Before
    public void setUp() throws Exception {
        filePath = "";
        fileName = "Screenshot";
        fileType = "jpg";
        file = new File(filePath + fileName + "." + fileType);
        screenshot = new Screenshot(filePath, fileName, fileType, 2000);
    }

    @Test
    public void testGetScreenshot() throws Exception {
        screenshot.getScreenshot();
        assertTrue(file.exists());
    }

    @After
    public void tearDown() throws Exception {
        file.delete();
    }
}