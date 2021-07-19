package com.baeldung.headlessmode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.awt.Canvas;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.image.BufferedImage;
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
            result = ImageIO.write(inputImage, FORMAT, outStream);
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

}
