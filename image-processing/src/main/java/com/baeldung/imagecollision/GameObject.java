package com.baeldung.imagecollision;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

public class GameObject {
    public int x, y;
    public int width, height;
    public BufferedImage image;

    int[] xPoints = new int[4];
    int[] yPoints = new int[4];

    int[] xOffsets = {100, 200, 100, 0};
    int[] yOffsets = {0, 170, 340, 170};

    public GameObject(int x, int y, BufferedImage image) {
        this.x = x;
        this.y = y;
        this.image = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
    }

    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, null);
    }

    public Rectangle getRectangleBounds() {
        return new Rectangle(x, y, width, height);
    }

    public Area getEllipseAreaBounds() {
        Ellipse2D.Double coll = new Ellipse2D.Double(x, y, width, height);
        return new Area(coll);
    }

    public Ellipse2D getCircleBounds() {
        return new Ellipse2D.Double(x, y, 200, 200);
    }

    public Area getPolygonBounds() {
        for (int i = 0; i < xOffsets.length; i++) {
            this.xPoints[i] = x + xOffsets[i];
            this.yPoints[i] = y + yOffsets[i];
        }
        Polygon p = new Polygon(xPoints, yPoints, xOffsets.length);

        return new Area(p);
    }

    public boolean collidesWith(GameObject other) {
        int top = Math.max(y, other.y);
        int bottom = Math.min(y + height, other.y + other.height);
        int left = Math.max(x, other.x);
        int right = Math.min(x + width, other.x + other.height);

        if (right <= left || bottom <= top) return false;

        for (int i = top; i < bottom; i++) {
            for (int j = left; j < right; j++) {
                int pixel1 = image.getRGB(j - x, i - y);
                int pixel2 = other.image.getRGB(j - other.x, i - other.y);

                if (((pixel1 >> 24) & 0xff) > 0 && ((pixel2 >> 24) & 0xff) > 0) {
                    return true;
                }
            }
        }
        return false;
    }
}

