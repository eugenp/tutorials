package com.baeldung.commons.compress;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.examples.Archiver;
import org.apache.commons.compress.archivers.examples.Expander;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.CompressorOutputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.apache.commons.compress.utils.FileNameUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

public class CompressUtils {

    private CompressUtils() {
    }

    public static void archive(Path directory, Path destination) throws IOException, ArchiveException {
        String format = FileNameUtils.getExtension(destination);
        new Archiver().create(format, destination, directory);
    }

    public static void archiveAndCompress(String directory, Path destination) throws IOException, ArchiveException, CompressorException {
        archiveAndCompress(Paths.get(directory), destination);
    }

    public static void archiveAndCompress(Path directory, Path destination) throws IOException, ArchiveException, CompressorException {
        String compressionFormat = FileNameUtils.getExtension(destination);
        String archiveFormat = FilenameUtils.getExtension(destination.getFileName()
            .toString()
            .replace("." + compressionFormat, ""));

        try (OutputStream archive = Files.newOutputStream(destination);
            BufferedOutputStream archiveBuffer = new BufferedOutputStream(archive);
            CompressorOutputStream compressor = new CompressorStreamFactory().createCompressorOutputStream(compressionFormat, archiveBuffer);
            ArchiveOutputStream<?> archiver = new ArchiveStreamFactory().createArchiveOutputStream(archiveFormat, compressor)) {
            new Archiver().create(archiver, directory);
        }
    }

    public static void decompress(Path file, Path destination) throws IOException, ArchiveException, CompressorException {
        decompress(Files.newInputStream(file), destination);
    }
    
    public static void decompress(InputStream file, Path destination) throws IOException, ArchiveException, CompressorException {
        try (InputStream in = file;
            BufferedInputStream inputBuffer = new BufferedInputStream(in);
            OutputStream out = Files.newOutputStream(destination);
            CompressorInputStream decompressor = new CompressorStreamFactory().createCompressorInputStream(inputBuffer)) {
            IOUtils.copy(decompressor, out);
        }
    }

    public static void extract(Path archive, Path destination) throws IOException, ArchiveException, CompressorException {
        new Expander().expand(archive, destination);
    }

    public static void compressFile(Path file, Path destination) throws IOException, CompressorException {
        String format = FileNameUtils.getExtension(destination);

        try (OutputStream out = Files.newOutputStream(destination); 
            BufferedOutputStream buffer = new BufferedOutputStream(out);
            CompressorOutputStream compressor = new CompressorStreamFactory().createCompressorOutputStream(format, buffer)) {
            IOUtils.copy(Files.newInputStream(file), compressor);
        }
    }

    public static void zip(Path file, Path destination) throws IOException {
        try (InputStream input = Files.newInputStream(file);
            OutputStream output = Files.newOutputStream(destination);
            ZipArchiveOutputStream archive = new ZipArchiveOutputStream(output)) {
            archive.setLevel(Deflater.BEST_COMPRESSION);
            archive.setMethod(ZipEntry.DEFLATED);

            archive.putArchiveEntry(new ZipArchiveEntry(file.getFileName()
                .toString()));
            IOUtils.copy(input, archive);
            archive.closeArchiveEntry();
        }
    }

    public static void extractOne(Path archivePath, String fileName, Path destinationDirectory) throws IOException, ArchiveException {
        try (InputStream input = Files.newInputStream(archivePath); 
            BufferedInputStream buffer = new BufferedInputStream(input); 
            ArchiveInputStream<?> archive = new ArchiveStreamFactory().createArchiveInputStream(buffer)) {

            ArchiveEntry entry;
            while ((entry = archive.getNextEntry()) != null) {
                if (entry.getName()
                    .equals(fileName)) {
                    Path outFile = destinationDirectory.resolve(fileName);
                    Files.createDirectories(outFile.getParent());
                    try (OutputStream os = Files.newOutputStream(outFile)) {
                        IOUtils.copy(archive, os);
                    }
                    break;
                }
            }
        }
    }
}