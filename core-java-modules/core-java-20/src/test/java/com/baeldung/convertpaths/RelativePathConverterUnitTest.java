package com.baeldung.convertpaths;

import org.junit.Test;

public class RelativePathConverterUnitTest {

    @Test
    public void testConvertToAbsolutePath() {
        String relativePath = "data/sample.txt";
        System.out.println(RelativePathConverter.convertToAbsoluteUsePathsClass(relativePath));
        System.out.println(RelativePathConverter.convertToAbsoluteUseFileClass(relativePath));
        System.out.println(RelativePathConverter.convertToAbsoluteUseFileSystemsClass(relativePath));
    }

    @Test
    public void testConvertToAbsolutePath_withAbsolutePath() {
        String absolutePath = "/var/www/index.html";
        System.out.println(RelativePathConverter.convertToAbsoluteUsePathsClass(absolutePath));
        System.out.println(RelativePathConverter.convertToAbsoluteUseFileClass(absolutePath));
        System.out.println(RelativePathConverter.convertToAbsoluteUseFileSystemsClass(absolutePath));
    }

    @Test
    public void testConvertToAbsolutePath_withEmptyPath() {
        String emptyPath = "";
        System.out.println(RelativePathConverter.convertToAbsoluteUsePathsClass(emptyPath));
        System.out.println(RelativePathConverter.convertToAbsoluteUseFileClass(emptyPath));
        System.out.println(RelativePathConverter.convertToAbsoluteUseFileSystemsClass(emptyPath));
    }

    @Test
    public void testConvertToAbsolutePath_withParentDirectory() {
        String relativePath = "../data/sample.txt";
        System.out.println(RelativePathConverter.convertToAbsoluteUsePathsClass(relativePath));
        System.out.println(RelativePathConverter.convertToAbsoluteUseFileClass(relativePath));
        System.out.println(RelativePathConverter.convertToAbsoluteUseFileSystemsClass(relativePath));
    }

    @Test
    public void testConvertToAbsolutePath_withRelativePathContainingDots() {
        String relativePath = "././data/sample.txt";
        System.out.println(RelativePathConverter.convertToAbsoluteUsePathsClass(relativePath));
        System.out.println(RelativePathConverter.convertToAbsoluteUseFileClass(relativePath));
        System.out.println(RelativePathConverter.convertToAbsoluteUseFileSystemsClass(relativePath));
    }
}
