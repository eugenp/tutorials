package com.baeldung.shallowvsdeepcopy;

public class Line implements Cloneable {

    private Point start;
    private Point end;

    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public Point getStart() {
        return start;
    }

    public void setStart(Point start) {
        this.start = start;
    }

    public Point getEnd() {
        return end;
    }

    public void setEnd(Point end) {
        this.end = end;
    }

    @Override
    protected Line clone() {
        return new Line(
          new Point(this.getStart().getX(), this.getStart().getY()),
          new Point(this.getStart().getX(), this.getStart().getY()));
    }
}
