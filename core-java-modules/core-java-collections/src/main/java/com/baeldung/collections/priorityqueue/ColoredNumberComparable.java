package com.baeldung.collections.priorityqueue;

public final class ColoredNumberComparable implements Comparable<ColoredNumberComparable> {
    private int value;
    private String color;

    public ColoredNumberComparable(int value, String color) {
        this.value = value;
        this.color = color;
    }

    @Override
    public int compareTo(ColoredNumberComparable o) {
        // (both numbers are red) or (both numbers are not red)
        if ((this.color.equals("red") && o.color.equals("red")) ||
                (!this.color.equals("red") && !o.color.equals("red"))) {
            return Integer.compare(this.value, o.value);
        }
        // only the first number is red
        else if (this.color.equals("red")) {
            return -1;
        }
        // only the second number is red
        else {
            return 1;
        }
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}