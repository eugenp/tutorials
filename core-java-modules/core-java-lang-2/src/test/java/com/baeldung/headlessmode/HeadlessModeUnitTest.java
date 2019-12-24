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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeadlessModeUnitTest {

    private static final String IN_FILE = "src/test/resources/product.png";
    private static final String OUT_FILE = "src/test/resources/product.jpg";
    private static final String FORMAT = "jpg";
    private static final Logger LOGGER = LoggerFactory.getLogger(HeadlessModeUnitTest.class);

    @Before
    public void setUpHeadlessMode() {
        System.setProperty("java.awt.headless", "true");
    }

    @Test
    public void whenSetUpSuccessful_thenHeadlessIsTrue() {
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
    public void whenHeadlessMode_thenImagesWork() {
        boolean result = false;
        try (FileInputStream inStream = new FileInputStream(IN_FILE); FileOutputStream outStream = new FileOutputStream(OUT_FILE)) {

            BufferedImage inputImage = ImageIO.read(inStream);

            result = ImageIO.write(inputImage, FORMAT, outStream);

        } catch (IOException e) {
            LOGGER.error(e.getMessage());
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

}
