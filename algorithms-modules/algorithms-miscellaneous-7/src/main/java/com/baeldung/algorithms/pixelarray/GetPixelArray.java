package com.baeldung.algorithms.pixelarray;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
public class GetPixelArray {

    public static int[][] get2DPixelArraySlow(BufferedImage sampleImage) {
        int width = sampleImage.getWidth();
        int height = sampleImage.getHeight();
        int[][] result = new int[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                result[row][col] = sampleImage.getRGB(col, row);
            }
        }

        return result;
    }

    public static int[][] get2DPixelArrayFast(BufferedImage image) {
        final byte[] pixelData = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        final int width = image.getWidth();
        final int height = image.getHeight();
        final boolean hasAlphaChannel = image.getAlphaRaster() != null;

        int[][] result = new int[height][width];
        if (hasAlphaChannel) {
            final int numberOfValues = 4;
            for (int valueIndex = 0, row = 0, col = 0; valueIndex + numberOfValues - 1 < pixelData.length; valueIndex += numberOfValues) {
                // Getting the values for each pixel from the pixelData array.
                int argb = 0;
                argb += (((int) pixelData[valueIndex] & 0xff) << 24); // alpha value
                argb += ((int) pixelData[valueIndex + 1] & 0xff); // blue value
                argb += (((int) pixelData[valueIndex + 2] & 0xff) << 8); // green value
                argb += (((int) pixelData[valueIndex + 3] & 0xff) << 16); // red value
                result[row][col] = argb;

                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }
        } else {
            final int numberOfValues = 3;
            for (int valueIndex = 0, row = 0, col = 0; valueIndex + numberOfValues - 1 < pixelData.length; valueIndex += numberOfValues) {
                int argb = 0;
                argb += -16777216; // 255 alpha value (fully opaque)
                argb += ((int) pixelData[valueIndex] & 0xff); // blue value
                argb += (((int) pixelData[valueIndex + 1] & 0xff) << 8); // green value
                argb += (((int) pixelData[valueIndex + 2] & 0xff) << 16); // red value
                result[row][col] = argb;

                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }
        }

        return result;
    }
}