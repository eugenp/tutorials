package com.baeldung.algorithms.fastgaussianblur;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import javax.imageio.ImageIO;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Fast Gaussian Blur approximation tester on a real RGB image
 */
public class FastGaussianBlurRealImageTester {

    private static final Logger LOGGER = LoggerFactory.getLogger(FastGaussianBlurRealImageTester.class);

    @Nonnull
    public static BufferedImage blurRealImage(@Nonnull BufferedImage image, int radius, int numPasses) {
        int width = image.getWidth();
        int height = image.getHeight();

        // 1. We extract 1D array of 32-bit ARGB pixels
        int[] pixels = image.getRGB(0, 0, width, height, null, 0, width);

        // 2. We define arrays to hold independent color channels
        int[] a = new int[pixels.length];
        int[] r = new int[pixels.length];
        int[] g = new int[pixels.length];
        int[] b = new int[pixels.length];

        // 3. We unpack the 32-bit integers into separate channels
        for (int i = 0; i < pixels.length; i++) {
            a[i] = (pixels[i] >> 24) & 0xff;
            r[i] = (pixels[i] >> 16) & 0xff;
            g[i] = (pixels[i] >> 8) & 0xff;
            b[i] = pixels[i] & 0xff;
        }

        // 4. We apply our O(n) FastGaussianBlur algorithm to each color channel independently
        r = FastGaussianBlur.applyFastGaussianBlur(r, width, height, radius, numPasses);
        g = FastGaussianBlur.applyFastGaussianBlur(g, width, height, radius, numPasses);
        b = FastGaussianBlur.applyFastGaussianBlur(b, width, height, radius, numPasses);

        // 5. We repack the channels back into a 32-bit integer array
        int[] resultPixels = new int[pixels.length];
        for (int i = 0; i < pixels.length; i++) {
            resultPixels[i] = (a[i] << 24) | (r[i] << 16) | (g[i] << 8) | b[i];
        }

        // 6. We create a new BufferedImage and set the blurred pixels
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        result.setRGB(0, 0, width, height, resultPixels, 0, width);

        // 7. We return the result
        return result;
    }

    public static void main(String[] args) throws Exception {
        long startTime = System.currentTimeMillis();
        int radius = 1;
        int numPasses = 5;

        // 1. We create a thread and read sapmple.jpg from ../src/man/resources
        InputStream is = Thread.currentThread()
            .getContextClassLoader()
            .getResourceAsStream("sample.jpg");

        if (is == null) {
            throw new RuntimeException("Resource not found: sample.jpg");
        }

        BufferedImage originalImage = ImageIO.read(is);
        assert originalImage != null;

        // 2. We run our Fast Blur algorithm
        BufferedImage blurredImage = blurRealImage(originalImage, radius, numPasses); // Radius 10
        long endTime = System.currentTimeMillis();

        LOGGER.debug("Blur completed in: " + (endTime - startTime) + " ms");

        //3. We save result image sample_blurred.jpg under algorithms-numeric/target
        File outputFile = new File("algorithms-numeric/target/sample_blurred.jpg");
        boolean status = ImageIO.write(blurredImage, "png", outputFile);
        LOGGER.debug("Blur Operation: " + status + " Saved to: " + outputFile.getAbsolutePath());
    }
}