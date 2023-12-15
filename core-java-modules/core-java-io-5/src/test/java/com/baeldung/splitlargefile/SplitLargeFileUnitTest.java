package com.baeldung.splitlargefile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.BeforeClass;

public class SplitLargeFileUnitTest {

    @BeforeClass
    public static void prepareData() throws IOException {
        Files.createDirectories(Paths.get("target/split"));
    }

    private String splitedFileDirPath() throws Exception {
        return Paths.get("target").toString() + "/split";
    }

    private Path largeFilePath() throws Exception {
        return Paths.get(this.getClass().getClassLoader().getResource("large-file.txt").toURI());
    }


    @org.junit.Test
    public void givenLargeFile_whenSplitLargeFile_thenSplitBySize() throws Exception {
        File input = largeFilePath().toFile();
        SplitLargeFile slf = new SplitLargeFile();
        slf.splitByFileSize(input, 1024_000, splitedFileDirPath());
    }

    @org.junit.Test
    public void givenLargeFile_whenSplitLargeFile_thenSplitByNumberOfFiles() throws Exception {
        File input = largeFilePath().toFile();
        SplitLargeFile slf = new SplitLargeFile();
        slf.splitByNumberOfFiles(input, 3, splitedFileDirPath());
    }

}
