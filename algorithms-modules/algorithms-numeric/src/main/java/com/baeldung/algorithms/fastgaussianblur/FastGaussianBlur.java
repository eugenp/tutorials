package com.baeldung.algorithms.fastgaussianblur;

/**
 * Fast Gaussian Blur approximation using sliding window over rows and columns
 */
public class FastGaussianBlur {
    /**
     * Horizontal box blur over rows
     *
     * @param source Source image row.
     * @param target Target image row.
     * @param width Image width.
     * @param height Image height.
     * @param radius Blur radius.
     */
    private static void horizontalBoxBlur(int[] source, int[] target, int width, int height, int radius) {
        double scale = 1.0 / (radius * 2 + 1);

        for (int y = 0; y < height; y++) {
            int windowSum = 0;
            int offset = y * width;

            // 1. We initialize the sliding window for the first pixel in the row
            for (int x = -radius; x <= radius; x++) {
                int safeX = Math.min(Math.max(x, 0), width - 1);
                windowSum += source[offset + safeX];
            }

            // 2. We slide the window across the row
            for (int x = 0; x < width; x++) {
                target[offset + x] = (int) Math.round(windowSum * scale);

                // 2a. We subtract the leaving pixel and add the entering pixel
                int leftX = Math.max(x - radius, 0);
                int rightX = Math.min(x + radius + 1, width - 1);

                // 2b. We update the sliding window
                windowSum -= source[offset + leftX];
                windowSum += source[offset + rightX];
            }
        }
    }

    /**
     * Vertical box blur over columns. It is identical in logic, but we just
     * traverse columns instead of rows
     *
     * @param source Source image row.
     * @param target Target image row.
     * @param width Image width.
     * @param height Image height.
     * @param radius Blur radius.
     */
    private static void verticalBoxBlur(int[] source, int[] target, int width, int height, int radius) {
        double scale = 1.0 / (radius * 2 + 1);

        for (int x = 0; x < width; x++) {
            int windowSum = 0;

            for (int y = -radius; y <= radius; y++) {
                int safeY = Math.min(Math.max(y, 0), height - 1);
                windowSum += source[safeY * width + x];
            }

            for (int y = 0; y < height; y++) {
                target[y * width + x] = (int) Math.round(windowSum * scale);

                int topY = Math.max(y - radius, 0);
                int bottomY = Math.min(y + radius + 1, height - 1);

                windowSum -= source[topY * width + x];
                windowSum += source[bottomY * width + x];
            }
        }
    }

    /**
     * Main orchestrator to call Fast Gaussian 2D Blur by separating it into
     * two 1D operations
     * @param source Source image row.
     * @param width Image width
     * @param height Image height
     * @param radius Blur radius
     * @param numPasses Number of Passes to Gaussian Approximate
     */
    public static int[] applyFastGaussianBlur(int[] source, int width, int height, int radius, int numPasses) {
        int[] target = new int[source.length];
        int[] temp = new int[source.length];

        // 1. Copy original image data to target
        System.arraycopy(source, 0, target, 0, source.length);

        // 2. Run (numPasses = 5) iterations for the Gaussian approximation
        for (int i = 0; i < numPasses; i++) {
            horizontalBoxBlur(target, temp, width, height, radius);
            verticalBoxBlur(temp, target, width, height, radius);
        }
        return target;
    }
}
