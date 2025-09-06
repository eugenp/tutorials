package com.baeldung.image.compression;


import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.IOException;

public class ThumbnailsCompressor {

    public static void compressImage(String inputPath, String outputPath) throws IOException {
        File input = new File(inputPath);
        File output = new File(outputPath);
        Thumbnails.of(input)
                .scale(1)
                .outputQuality(0.5)
                .toFile(output);
    }
}