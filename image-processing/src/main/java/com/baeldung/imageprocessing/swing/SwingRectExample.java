package com.baeldung.imageprocessing.swing;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SwingRectExample {
    public static void main(String[] args) throws IOException {
        BufferedImage image  = loadImage();
        drawRectangle(image);
        displayImage(image);
    }

    private static BufferedImage loadImage() throws IOException {
        String imagePath = SwingRectExample.class.getClassLoader().getResource("lena.jpg").getPath();
        return ImageIO.read(new File(imagePath));
    }

    private static void drawRectangle(BufferedImage image) {
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setStroke(new BasicStroke(3));
        g.setColor(Color.BLUE);
        g.drawRect(10, 10, image.getWidth() - 20, image.getHeight() - 20);
    }

    private static void displayImage(BufferedImage image) {
        JLabel picLabel = new JLabel(new ImageIcon(image));

        JPanel jPanel = new JPanel();
        jPanel.add(picLabel);

        JFrame f = new JFrame();
        f.setSize(new Dimension(image.getWidth(), image.getHeight()));
        f.add(jPanel);
        f.setVisible(true);
    }
}
