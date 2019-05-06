package com.baeldung.java9;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.awt.Image;
import java.awt.image.BaseMultiResolutionImage;
import java.awt.image.BufferedImage;
import java.awt.image.MultiResolutionImage;
import java.util.List;

import org.junit.Test;

public class MultiResultionImageUnitTest {

    @Test
    public void baseMultiResImageTest() {
        int baseIndex = 1;
        int length = 4;
        BufferedImage[] resolutionVariants = new BufferedImage[length];
        for (int i = 0; i < length; i++) {
            resolutionVariants[i] = createImage(i);
        }
        MultiResolutionImage bmrImage = new BaseMultiResolutionImage(baseIndex, resolutionVariants);
        List<Image> rvImageList = bmrImage.getResolutionVariants();
        assertEquals("MultiResoltion Image shoudl contain the same number of resolution variants!", rvImageList.size(), length);

        for (int i = 0; i < length; i++) {
            int imageSize = getSize(i);
            Image testRVImage = bmrImage.getResolutionVariant(imageSize, imageSize);
            assertSame("Images should be the same", testRVImage, resolutionVariants[i]);
        }

    }

    private static int getSize(int i) {
        return 8 * (i + 1);
    }

    private static BufferedImage createImage(int i) {
        return new BufferedImage(getSize(i), getSize(i), BufferedImage.TYPE_INT_RGB);
    }

}
