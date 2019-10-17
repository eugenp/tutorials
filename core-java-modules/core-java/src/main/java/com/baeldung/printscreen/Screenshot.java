package com.baeldung.printscreen;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

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
