package com.baeldung.algorithms.rectanglesoverlap;

public class Rectangle {

    private Point bottomLeft;
    private Point topRight;

    public Rectangle(Point bottomLeft, Point topRight) {
        this.bottomLeft = bottomLeft;
        this.topRight = topRight;
    }

    public Point getBottomLeft() {
        return bottomLeft;
    }

    public void setBottomLeft(Point bottomLeft) {
        this.bottomLeft = bottomLeft;
    }

    public Point getTopRight() {
        return topRight;
    }

    public void setTopRight(Point topRight) {
        this.topRight = topRight;
    }

    public boolean isOverlapping(Rectangle comparedRectangle) {
        // one rectangle is to the top of the comparedRectangle
        if (this.topRight.getY() < comparedRectangle.bottomLeft.getY() || this.bottomLeft.getY() > comparedRectangle.topRight.getY()) {
            return false;
        }
        // one rectangle is to the left of the comparedRectangle
        if (this.topRight.getX() < comparedRectangle.bottomLeft.getX() || this.bottomLeft.getX() > comparedRectangle.topRight.getX()) {
            return false;
        }
        return true;
    }

    public boolean isOverlappingWithoutBorders(Rectangle comparedRectangle) {
        // one rectangle is to the top of the comparedRectangle
        if (this.topRight.getY() <= comparedRectangle.bottomLeft.getY() || this.bottomLeft.getY() >= comparedRectangle.topRight.getY()) {
            return false;
        }
        // one rectangle is to the left of the comparedRectangle
        if (this.topRight.getX() <= comparedRectangle.bottomLeft.getX() || this.bottomLeft.getX() >= comparedRectangle.topRight.getX()) {
            return false;
        }
        return true;
    }
}
