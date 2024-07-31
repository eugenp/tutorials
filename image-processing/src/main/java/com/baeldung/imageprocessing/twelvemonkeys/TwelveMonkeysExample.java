package com.baeldung.imageprocessing.twelvemonkeys;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TwelveMonkeysExample {
    public static void main(String[] args) throws IOException {
        BufferedImage image  = loadImage();
        drawRectangle(image);
        displayImage(image);
    }

    private static BufferedImage loadImage() throws IOException {
        String imagePath = TwelveMonkeysExample.class.getClassLoader().getResource("Penguin.ico").getPath();
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
        f.setSize(new Dimension(200, 200));
        f.add(jPanel);
        f.setVisible(true);
    }
}
