package com.baeldung.commons.untar;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class TarExtractor {

    private InputStream tarStream;
    private boolean gzip;
    private Path destination;

    public TarExtractor(InputStream in, boolean gzip, Path destination) throws IOException {
        this.tarStream = in;
        this.gzip = gzip;
        this.destination = destination;

        Files.createDirectories(destination);
    }

    public TarExtractor(Path tarFile, Path destination) throws IOException {
        this(Files.newInputStream(tarFile), tarFile.endsWith("gz"), destination);
    }

    public Path getDestination() {
        return destination;
    }

    public InputStream getTarStream() {
        return tarStream;
    }

    public boolean isGzip() {
        return gzip;
    }

    abstract public void untar() throws IOException;
}
