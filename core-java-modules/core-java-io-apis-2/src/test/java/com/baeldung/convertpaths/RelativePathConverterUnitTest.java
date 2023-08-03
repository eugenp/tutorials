package com.baeldung.convertpaths;

import org.junit.Test;

public class RelativePathConverterUnitTest {

    @Test
    public void givenRelativePath_whenConvertingToAbsolutePath_thenPrintOutput() {
        String relativePath = "data/sample.txt";

        System.out.println(RelativePathConverter.convertToAbsoluteUsePathsClass(relativePath));
        System.out.println(RelativePathConverter.convertToAbsoluteUseFileClass(relativePath));
        System.out.println(RelativePathConverter.convertToAbsoluteUseFileSystemsClass(relativePath));
    }

    @Test
    public void givenAbsolutePath_whenConvertingToAbsolutePath_thenPrintOutput() {
        String absolutePath = "/var/www/index.html";

        System.out.println(RelativePathConverter.convertToAbsoluteUsePathsClass(absolutePath));
        System.out.println(RelativePathConverter.convertToAbsoluteUseFileClass(absolutePath));
        System.out.println(RelativePathConverter.convertToAbsoluteUseFileSystemsClass(absolutePath));
    }

    @Test
    public void givenEmptyPath_whenConvertingToAbsolutePath_thenPrintOutput() {
        String emptyPath = "";

        System.out.println(RelativePathConverter.convertToAbsoluteUsePathsClass(emptyPath));
        System.out.println(RelativePathConverter.convertToAbsoluteUseFileClass(emptyPath));
        System.out.println(RelativePathConverter.convertToAbsoluteUseFileSystemsClass(emptyPath));
    }

    @Test
    public void givenParentDirectoryPath_whenConvertingToAbsolutePath_thenPrintOutput() {
        String relativePath = "../data/sample.txt";

        System.out.println(RelativePathConverter.convertToAbsoluteUsePathsClass(relativePath));
        System.out.println(RelativePathConverter.convertToAbsoluteUseFileClass(relativePath));
        System.out.println(RelativePathConverter.convertToAbsoluteUseFileSystemsClass(relativePath));
    }

    @Test
    public void givenRelativePathContainingDots_whenConvertingToAbsolutePath_thenPrintOutput() {
        String relativePath = "././data/sample.txt";

        System.out.println(RelativePathConverter.convertToAbsoluteUsePathsClass(relativePath));
        System.out.println(RelativePathConverter.convertToAbsoluteUseFileClass(relativePath));
        System.out.println(RelativePathConverter.convertToAbsoluteUseFileSystemsClass(relativePath));
    }
}
