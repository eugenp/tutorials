package com.baeldung.java9.streams.reactive.flowvsrx;

class VideoFrame {
    private long number;

    public VideoFrame(long number) {
        this.number = number;
    }

    public long getNumber() {
        return number;
    }
}