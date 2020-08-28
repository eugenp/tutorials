package com.baeldung.image.resize.thumbnailator;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;

public class ThumbnailatorExample {
    static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Thumbnails.of(originalImage)
            .size(targetWidth, targetHeight)
            .outputFormat("JPEG")
            .outputQuality(0.90)
            .toOutputStream(outputStream);
        byte[] data = outputStream.toByteArray();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        return ImageIO.read(inputStream);
    }

    public static void main(String[] args) throws Exception {
        BufferedImage originalImage = ImageIO.read(new File("src/main/resources/images/sampleImage.jpg"));
        BufferedImage outputImage = resizeImage(originalImage, 200, 200);
        ImageIO.write(outputImage, "jpg", new File("src/main/resources/images/sampleImage-resized-thumbnailator.jpg"));
    }
}
