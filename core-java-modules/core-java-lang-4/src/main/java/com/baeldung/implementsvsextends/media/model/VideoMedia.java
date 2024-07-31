package com.baeldung.implementsvsextends.media.model;

public class VideoMedia extends Media {

    private String resolution;

    private String aspectRatio;

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(String aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    @Override
    public String toString() {
        return "VideoMedia{" +
                "id=" + this.getId() +
                ", title='" + this.getTitle() + '\'' +
                ", artist='" + this.getArtist() + '\'' +
                "resolution='" + resolution + '\'' +
                ", aspectRatio='" + aspectRatio + '\'' +
                "} ";
    }
}