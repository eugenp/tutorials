package com.baeldung.printscreen;

import org.junit.After;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertTrue;

public class ScreenshotLiveTest {

    private Screenshot screenshot = new Screenshot("Screenshot.jpg");
    private File file = new File("Screenshot.jpg");

    @Test
    public void testGetScreenshot() throws Exception {
        screenshot.getScreenshot(2000);
        assertTrue(file.exists());
    }

    @After
    public void tearDown() throws Exception {
        file.delete();
    }
}