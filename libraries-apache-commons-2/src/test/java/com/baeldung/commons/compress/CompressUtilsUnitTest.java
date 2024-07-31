package com.baeldung.commons.compress;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CompressUtilsUnitTest {

    static Path TMP;
    static String ZIP_FILE = "new.txt.zip";
    static String COMPRESSED_FILE = "new.txt.gz";
    static String DECOMPRESSED_FILE = "decompressed-file.txt";
    static String DECOMPRESSED_ARCHIVE = "decompressed-archive.tar";
    static String COMPRESSED_ARCHIVE = "archive.tar.gz";
    static String MODIFIED_ARCHIVE = "modified-archive.tar";
    static String EXTRACTED_DIR = "extracted";

    @BeforeAll
    static void setup() throws IOException {
        TMP = Files.createTempDirectory("compress-test")
            .toAbsolutePath();
    }

    @AfterAll
    static void destroy() throws IOException {
        FileUtils.deleteDirectory(TMP.toFile());
    }

    @Test
    @Order(1)
    void givenFile_whenCompressing_thenCompressed() throws IOException, CompressorException, URISyntaxException {
        Path destination = TMP.resolve(COMPRESSED_FILE);

        CompressUtils.compressFile(TestResources.testFile(), destination);

        assertTrue(Files.isRegularFile(destination));
    }

    @Test
    @Order(2)
    void givenFile_whenZipping_thenZipFileCreated() throws IOException, URISyntaxException {
        Path destination = TMP.resolve(ZIP_FILE);

        CompressUtils.zip(TestResources.testFile(), destination);

        assertTrue(Files.isRegularFile(destination));
    }

    @Test
    @Order(3)
    void givenCompressedArchive_whenDecompressing_thenArchiveAvailable() throws IOException, ArchiveException, CompressorException {
        Path destination = TMP.resolve(DECOMPRESSED_ARCHIVE);

        CompressUtils.decompress(TestResources.compressedArchive(), destination);

        assertTrue(Files.isRegularFile(destination));
    }

    @Test
    @Order(4)
    void givenCompressedFile_whenDecompressing_thenFileAvailable() throws IOException, ArchiveException, CompressorException {
        Path destination = TMP.resolve(DECOMPRESSED_FILE);

        CompressUtils.decompress(TMP.resolve(COMPRESSED_FILE), destination);

        assertTrue(Files.isRegularFile(destination));
    }

    @Test
    @Order(5)
    void givenDecompressedArchive_whenUnarchiving_thenFilesAvailable() throws IOException, ArchiveException, CompressorException {
        Path destination = TMP.resolve(EXTRACTED_DIR);

        CompressUtils.extract(TMP.resolve(DECOMPRESSED_ARCHIVE), destination);

        assertTrue(Files.isDirectory(destination));
    }

    @Test
    @Order(6)
    void givenDirectory_whenArchivingAndCompressing_thenCompressedArchiveAvailable() throws IOException, ArchiveException, CompressorException {
        Path destination = TMP.resolve(COMPRESSED_ARCHIVE);

        CompressUtils.archiveAndCompress(TMP.resolve(EXTRACTED_DIR), destination);

        assertTrue(Files.isRegularFile(destination));
    }

    @Test
    @Order(7)
    void givenExistingArchive_whenAddingSingleEntry_thenArchiveModified() throws IOException, ArchiveException, CompressorException, URISyntaxException {
        Path archive = TMP.resolve(DECOMPRESSED_ARCHIVE);
        Path newArchive = TMP.resolve(MODIFIED_ARCHIVE);
        Path tmpDir = TMP.resolve(newArchive + "-tmpd");

        Path newEntry = TestResources.testFile();

        CompressUtils.extract(archive, tmpDir);
        assertTrue(Files.isDirectory(tmpDir));

        Files.copy(newEntry, tmpDir.resolve(newEntry.getFileName()));
        CompressUtils.archive(tmpDir, newArchive);
        assertTrue(Files.isRegularFile(newArchive));

        FileUtils.deleteDirectory(tmpDir.toFile());
        Files.delete(archive);
        Files.move(newArchive, archive);
        assertTrue(Files.isRegularFile(archive));
    }

    @Test
    @Order(8)
    void givenExistingArchive_whenExtractingSingleEntry_thenFileExtracted() throws IOException, ArchiveException {
        Path archive = TMP.resolve(DECOMPRESSED_ARCHIVE);
        String targetFile = "sub/other.txt";

        CompressUtils.extractOne(archive, targetFile, TMP);

        assertTrue(Files.isRegularFile(TMP.resolve(targetFile)));
    }
}
