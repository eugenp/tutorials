package com.baeldung.openhtmlfiles;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

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
            Assumptions.assumeFalse(GraphicsEnvironment.isHeadless(), "No display available");
            pb = new ProcessBuilder("xdg-open", absolutePath);
        }
        Process process = pb.start();
        assertNotNull(process);
    }
}
