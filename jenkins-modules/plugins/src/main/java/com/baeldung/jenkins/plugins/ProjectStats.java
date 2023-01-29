package com.baeldung.jenkins.plugins;

public class ProjectStats {

    private final int classesNumber;
    private final int linesNumber;

    public ProjectStats(int classesNumber, int linesNumber) {
        this.classesNumber = classesNumber;
        this.linesNumber = linesNumber;
    }

    public int getClassesNumber() {
        return classesNumber;
    }

    public int getLinesNumber() {
        return linesNumber;
    }
}
