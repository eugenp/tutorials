package com.baeldung.path;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

import javax.swing.filechooser.FileSystemView;

public class DesktopPathUnitTest {
    // Adapt DESKTOP_PATH variable to your own system path
    // private static final String DESKTOP_PATH = "C:\\Users\\HRAF\\Desktop";

    @Test
    public void whenUsingGetUserHomeProperty_thenShouldEqualDesktopPath() {
        String desktopPath = System.getProperty("user.home") + File.separator + "Desktop";
        // assertEquals(DESKTOP_PATH, desktopPath);
    }

    @Test
    public void whenUsingFileSystemViewGetHomeDirectory_thenShouldEqualDesktopPath() {
        FileSystemView view = FileSystemView.getFileSystemView();
        File file = view.getHomeDirectory();
        String path = file.getPath();
        // assertEquals(DESKTOP_PATH, path);
    }

}
