package com.baeldung.jar;

public class Rectangle {
    private int length;
    private int width;

    public Rectangle(int length, int width) {
        this.length = length;
        this.width = width;
    }

    public int area() {
        return length * width;
    }

    public int perimeter() {
        return (length * 2) + (width * 2);
    }

    public void printArea() {
        System.out.println("Area: " + area());
    }

    public void printPerimeter() {
        System.out.println("Perimeter: " + perimeter());
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

}
