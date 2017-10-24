package com.baeldung.polymorphism;

import java.awt.image.BufferedImage;

public class FileManager {

    public static void main(String[] args) {
        GenericFile file1 = new TextFile("SampleTextFile", "This is a sample text content", "v1.0.0");
        System.out.println("File Info: \n" + file1.getFileInfo() + "\n");
        ImageFile imageFile = new ImageFile("SampleImageFile", 200, 100, new BufferedImage(100, 200, BufferedImage.TYPE_INT_RGB).toString()
            .getBytes(), "v1.0.0");
        System.out.println("File Info: \n" + imageFile.getFileInfo());
    }

    public static ImageFile createImageFile(String name, int height, int width, byte[] content, String version) {
        ImageFile imageFile = new ImageFile(name, height, width, content, version);
        System.out.println("File 2 Info: \n" + imageFile.getFileInfo());
        return imageFile;
    }

    public static GenericFile createTextFile(String name, String content, String version) {
        GenericFile file1 = new TextFile(name, content, version);
        System.out.println("File 1 Info: \n" + file1.getFileInfo() + "\n");
        return file1;
    }

}
