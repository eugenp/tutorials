package com.baeldung.imagecollision;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Game extends JPanel implements Runnable {
    GameObject vader, luke;
    BufferedImage vaderImage, lukeImage;
    Thread gameThread;

    boolean collided = false;

    enum CollisionType {
        BOUNDING_BOX,
        AREA_CIRCLE,
        CIRCLE_DISTANCE,
        POLYGON,
        PIXEL_PERFECT
    }

    CollisionType collisionType = CollisionType.PIXEL_PERFECT;

    public Game() {
        try {
            vaderImage = ImageIO.read(new File("src/main/resources/images/vader.png"));
            lukeImage = ImageIO.read(new File("src/main/resources/images/luke.png"));

            vader = new GameObject(170, 370, vaderImage);
            luke = new GameObject(1600, 370, lukeImage);

        } catch (IOException e) {
            System.err.println("Error loading images");
            e.printStackTrace();
        }

        setBackground(Color.white);
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run() {
        while (!collided) {
            vader.move(2, 0);
            luke.move(-2, 0);

            switch (collisionType) {
                case BOUNDING_BOX:
                    if (vader.getRectangleBounds().intersects(luke.getRectangleBounds())) {
                        collided = true;
                    }
                    break;

                case AREA_CIRCLE:
                    Area ellipseAreaVader = vader.getEllipseAreaBounds();
                    Area ellipseAreaLuke = luke.getEllipseAreaBounds();
                    ellipseAreaVader.intersect(ellipseAreaLuke);

                    if (!ellipseAreaVader.isEmpty()) {
                        collided = true;
                    }
                    break;

                case CIRCLE_DISTANCE:
                    Ellipse2D circleVader = vader.getCircleBounds();
                    Ellipse2D circleLuke = luke.getCircleBounds();
                    double dx = circleVader.getCenterX() - circleLuke.getCenterX();
                    double dy = circleVader.getCenterY() - circleLuke.getCenterY();
                    double distance = Math.sqrt(dx * dx + dy * dy);
                    double radiusVader = circleVader.getWidth() / 2.0;
                    double radiusLuke = circleLuke.getWidth() / 2.0;

                    if (distance < radiusVader + radiusLuke) {
                        collided = true;
                    }
                    break;

                case POLYGON:
                    Area polygonAreaVader = vader.getPolygonBounds();
                    Area polygonAreaLuke = luke.getPolygonBounds();
                    polygonAreaVader.intersect(polygonAreaLuke);

                    if (!polygonAreaVader.isEmpty()) {
                        collided = true;
                    }
                    break;

                case PIXEL_PERFECT:
                    if (vader.collidesWith(luke)) {
                        collided = true;
                    }
                    break;
            }

            repaint();

            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        vader.draw(g);
        luke.draw(g);

        if (collided) {
            g.setColor(Color.RED);
            g.setFont(new Font("SansSerif", Font.BOLD, 50));
            g.drawString("COLLISION!", getWidth() / 2 - 100, getHeight() / 2);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Epic Duel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1920, 1080);
            frame.add(new Game());
            frame.setVisible(true);
        });
    }
}