package com.baeldung.implementsvsextends.media.model;

public class AudioMedia extends Media {

    private int bitrate;

    private String frequency;

    @Override
    public void printTitle() {
        System.out.println("AudioMedia Title");
    }

    public int getBitrate() {
        return bitrate;
    }

    public void setBitrate(int bitrate) {
        this.bitrate = bitrate;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }


    @Override
    public String toString() {
        return "AudioMedia{" +
                "id=" + this.getId() +
                ", title='" + this.getTitle() + '\'' +
                ", artist='" + this.getArtist() + '\'' +
                ", bitrate=" + bitrate +
                ", frequency='" + frequency + '\'' +
                "} ";
    }
}