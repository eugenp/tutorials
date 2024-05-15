package com.baeldung.headlessmode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.WritableRaster;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

public class HeadlessModeUnitTest {

    private static final String IN_FILE = "/product.png";
    private static final String OUT_FILE = System.getProperty("java.io.tmpdir") + "/product.jpg";
    private static final String FORMAT = "jpg";

    @Before
    public void setUpHeadlessMode() {
        System.setProperty("java.awt.headless", "true");
    }

    @Test
    public void whenJavaAwtHeadlessSetToTrue_thenIsHeadlessReturnsTrue() {
        assertThat(GraphicsEnvironment.isHeadless()).isTrue();
    }

    @Test
    public void whenHeadlessMode_thenFontsWork() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

        String fonts[] = ge.getAvailableFontFamilyNames();

        assertThat(fonts).isNotEmpty();

        Font font = new Font(fonts[0], Font.BOLD, 14);

        FontMetrics fm = (new Canvas()).getFontMetrics(font);

        assertThat(fm.getHeight()).isGreaterThan(0);
        assertThat(fm.getAscent()).isGreaterThan(0);
        assertThat(fm.getDescent()).isGreaterThan(0);
    }

    @Test
    public void whenHeadlessMode_thenImagesWork() throws IOException {
        boolean result = false;
        try (InputStream inStream = HeadlessModeUnitTest.class.getResourceAsStream(IN_FILE); FileOutputStream outStream = new FileOutputStream(OUT_FILE)) {
            BufferedImage inputImage = ImageIO.read(inStream);
            result = ImageIO.write(removeAlphaChannel(inputImage), FORMAT, outStream);
        }

        assertThat(result).isTrue();
    }

    @Test
    public void whenHeadlessmode_thenFrameThrowsHeadlessException() {
        assertThatExceptionOfType(HeadlessException.class).isThrownBy(() -> {
            Frame frame = new Frame();
            frame.setVisible(true);
            frame.setSize(120, 120);
        });
    }    

    @Test
    public void whenHeadless_thenFlexibleAppAdjustsItsBehavior() {
        assertThat(FlexibleApp.iAmFlexible()).isEqualTo(FlexibleApp.HEADLESS);
    }

    @Test
    public void whenHeaded_thenFlexibleAppAdjustsItsBehavior() {
        Assume.assumeFalse(GraphicsEnvironment.isHeadless());
        assertThat(FlexibleApp.iAmFlexible()).isEqualTo(FlexibleApp.HEADED);
    }

    private BufferedImage removeAlphaChannel(BufferedImage inputImage) {
        final WritableRaster raster = inputImage.getRaster();
        final WritableRaster newRaster = raster.createWritableChild(0, 0, inputImage.getWidth(), inputImage.getHeight(), 0, 0, new int[]{0, 1, 2});
        ColorModel newCM = new ComponentColorModel(inputImage.getColorModel().getColorSpace(), false, false, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
        return new BufferedImage(newCM, newRaster, false, null);
    }
}
