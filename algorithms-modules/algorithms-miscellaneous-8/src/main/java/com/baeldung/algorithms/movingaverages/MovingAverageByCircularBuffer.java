package com.baeldung.algorithms.movingaverages;

public class MovingAverageByCircularBuffer {

    private final double[] buffer;
    private int head;
    private int count;

    public MovingAverageByCircularBuffer(int windowSize) {
        this.buffer = new double[windowSize];
    }

    public void add(double value) {
        buffer[head] = value;
        head = (head + 1) % buffer.length;
        if (count < buffer.length) {
            count++;
        }
    }

    public double getMovingAverage() {
        if (count == 0) {
            return Double.NaN;
        }
        double sum = 0;
        for (int i = 0; i < count; i++) {
            sum += buffer[i];
        }
        return sum / count;
    }
}
