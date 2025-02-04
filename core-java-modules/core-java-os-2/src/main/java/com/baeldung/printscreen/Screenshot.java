package com.baeldung.printscreen;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Screenshot {

    private final String path;

    public Screenshot(String path) {
        this.path = path;
    }

    public void getScreenshot(int timeToWait) throws Exception {
        Rectangle rectangle = new Rectangle(Toolkit.getDefaultToolkit()
            .getScreenSize());
        Robot robot = new Robot();
        BufferedImage img = robot.createScreenCapture(rectangle);
        ImageIO.write(img, "jpg", new File(path));
    }
}
