package com.baeldung.openhtmlfiles;

import org.junit.Test;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeFalse;

public class OpenHtmlFilesUnitTest {
    public URL url;
    public String absolutePath;

    public OpenHtmlFilesUnitTest() throws URISyntaxException {
        url = getClass().getResource("/test.html");
        assertNotNull(url);
        File file = new File(url.toURI());
        assertTrue(file.exists());
        absolutePath = file.getAbsolutePath();
    }

    @Test
    public void givenHtmlFile_whenUsingDesktopClass_thenOpenFileInDefaultBrowser() throws IOException {
        assumeFalse("No display available", GraphicsEnvironment.isHeadless());
        assumeTrue(Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE));

        Desktop.getDesktop().browse(new File(absolutePath).toURI());
    }

    @Test
    public void givenHtmlFile_whenUsingProcessBuilder_thenOpenFileInDefaultBrowser() throws IOException {
        assumeFalse("No display available", GraphicsEnvironment.isHeadless());

        String os = System.getProperty("os.name").toLowerCase();
        ProcessBuilder pb;
        if (os.contains("win")) {
            pb = new ProcessBuilder("cmd.exe", "/c", "start", "", absolutePath);
        } else if (os.contains("mac")) {
            pb = new ProcessBuilder("open", absolutePath);
        } else {
            pb = new ProcessBuilder("xdg-open", absolutePath);
        }

        Process process = pb.start();
        assertNotNull(process);
    }
}
