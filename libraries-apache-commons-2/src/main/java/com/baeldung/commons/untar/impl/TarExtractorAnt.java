package com.baeldung.commons.untar.impl;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.zip.GZIPInputStream;

import org.apache.tools.tar.TarEntry;
import org.apache.tools.tar.TarInputStream;

import com.baeldung.commons.untar.TarExtractor;

public class TarExtractorAnt extends TarExtractor {

    public TarExtractorAnt(InputStream in, boolean gzip, Path destination) throws IOException {
        super(in, gzip, destination);
    }

    @Override
    public void untar() throws IOException {
        try (TarInputStream tar = new TarInputStream(new BufferedInputStream( //
                isGzip() ? new GZIPInputStream(getTarStream()) : getTarStream()))) {
            TarEntry entry;
            while ((entry = tar.getNextEntry()) != null) {
                Path extractTo = getDestination().resolve(entry.getName());
                if (entry.isDirectory()) {
                    Files.createDirectories(extractTo);
                } else {
                    Files.copy(tar, extractTo, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
    }
}