package com.baeldung.hexagonalarchitecture.ports;

public interface INoteWriter {

        void write(String text, INoteRepo repo);
}
