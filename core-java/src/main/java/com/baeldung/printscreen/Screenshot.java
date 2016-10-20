package com.baeldung.printscreen;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Screenshot {

    private final String filePath;
    private final String filenamePrefix;
    private final String fileType;

    public Screenshot(String filePath, String filenamePrefix, String fileType) {
        this.filePath = filePath;
        this.filenamePrefix = filenamePrefix;
        this.fileType = fileType;
    }

    public void getScreenshot(int timeToWait) throws Exception {
        Thread.sleep(timeToWait);
        Rectangle rectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        Robot robot = new Robot();
        BufferedImage img = robot.createScreenCapture(rectangle);
        ImageIO.write(img, fileType, setupFileNamePath());
    }

    private File setupFileNamePath() {
        return new File(filePath + filenamePrefix + "." + fileType);
    }

    private Rectangle getScreenSizedRectangle(final Dimension d) {
        return new Rectangle(0, 0, d.width, d.height);
    }
}
