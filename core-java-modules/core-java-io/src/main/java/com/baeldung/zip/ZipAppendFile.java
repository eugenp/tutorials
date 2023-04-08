package com.baeldung.zip;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static java.util.stream.Collectors.toList;

public class ZipAppendFile {
    public static void main(final String[] args) throws IOException {
        ZipMultipleFiles.main(args);
        String file3 = ZipAppendFile.class.getClassLoader().getResource("zipTest/file3.txt").getPath();
        Map<String, String> env = new HashMap<>();
        env.put("create", "true");
        Path path = Paths.get(Paths.get(file3).getParent() + "/compressed.zip");
        URI uri = URI.create("jar:" + path.toUri());
        try (FileSystem fs = FileSystems.newFileSystem(uri, env)) {
            Path nf = fs.getPath("newFile3.txt");
            Files.write(nf, Files.readAllBytes(Paths.get(file3)), StandardOpenOption.CREATE);
        }
    }
}