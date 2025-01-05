package com.baeldung.printscreen;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.Test;

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