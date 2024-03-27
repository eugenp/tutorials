package com.baeldung.openhtmlfiles;

import org.junit.Test;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import static org.junit.Assert.*;

public class OpenHtmlFilesUnitTest {
    public URL url;
    public String absolutePath;

    public OpenHtmlFilesUnitTest() throws URISyntaxException {
        url = getClass().getResource("/test.html");
        assert url != null;
        File file = new File(url.toURI());
        if (!file.exists()) {
            fail();
        }
        absolutePath = file.getAbsolutePath();
    }
    /*
    @Test
    public void givenHtmlFile_whenUsingDesktopClass_thenOpenFileInDefaultBrowser() throws IOException {
        File htmlFile = new File(absolutePath);
        Desktop.getDesktop().browse(htmlFile.toURI());
        assertTrue(true);
    }
    */
    @Test
    public void givenHtmlFile_whenUsingProcessBuilder_thenOpenFileInDefaultBrowser() throws IOException {
        ProcessBuilder pb;
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            pb = new ProcessBuilder("cmd.exe", "/c", "start", absolutePath);
        } else {
            pb = new ProcessBuilder("xdg-open", absolutePath);
        }
        pb.start();
        assertTrue(true);
    }
}
