package org.baeldung.corejava;;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Screenshot {

    private String filePath;
    private String filenamePrefix;
    private String fileType;
    private int timeToWait;

    public Screenshot(String filePath, String filenamePrefix,
                      String fileType, int timeToWait) {
        this.filePath = filePath;
        this.filenamePrefix = filenamePrefix;
        this.fileType = fileType;
        this.timeToWait = timeToWait;
    }

    public void getScreenshot() {
        try {
            Thread.sleep(timeToWait);
            Rectangle rectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            Robot robot = new Robot();
            BufferedImage img = robot.createScreenCapture(rectangle);
            ImageIO.write(img, fileType, setupFileNamePath());
        } catch (Exception ex) {
            System.out.println("Error occurred while getting the Print Screen.");
        }
    }

    private File setupFileNamePath() {
        return new File(filePath + filenamePrefix + "." + fileType);
    }

    private Rectangle getScreenSizedRectangle(final Dimension d) {
        return new Rectangle(0, 0, d.width, d.height);
    }
}
