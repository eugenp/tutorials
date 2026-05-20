package com.baeldung.algorithms.fastgaussianblur;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Fast Gaussian Blur JUnit Test case
 */
class FastGaussianBlurUnitTest {
    @Test
    void givenSharpImage_whenAppliedBlur_thenCenterIsSmoothed() {
        int width = 5;
        int height = 5;
        int numPasses = 5;
        int[] image = new int[width * height];

        // 1. We create an impulse image with all indices black image except one bright white pixel in the center.
        image[12] = 255;

        int[] blurredImage = FastGaussianBlur.applyFastGaussianBlur(image, width, height, 1, numPasses);

        // 2. We expect the center pixel should lose intensity
        // as it gets spread to its neighbors
        assertTrue(blurredImage[12] < 255);
        assertTrue(blurredImage[12] > 0);

        // 3. We expect that its immediate neighbors should have
        // gained intensity
        assertTrue(blurredImage[11] > 0); // Left neighbor
        assertTrue(blurredImage[13] > 0); // Right neighbor
    }
}