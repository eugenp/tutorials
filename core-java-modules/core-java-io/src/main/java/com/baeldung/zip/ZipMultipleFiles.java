package com.baeldung.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipMultipleFiles {
    public static void main(final String[] args) throws IOException {
        String file1 = ZipAppendFile.class.getClassLoader().getResource("zipTest/test1.txt").getPath();
        String file2 = ZipAppendFile.class.getClassLoader().getResource("zipTest/test2.txt").getPath();
        final List<String> srcFiles = Arrays.asList(file1, file2);
        final FileOutputStream fos =
                new FileOutputStream(Paths.get(file1).getParent().toAbsolutePath() + "/compressed.zip");
        final ZipOutputStream zipOut = new ZipOutputStream(fos);
        for (final String srcFile : srcFiles) {
            final File fileToZip = new File(srcFile);
            final FileInputStream fis = new FileInputStream(fileToZip);
            final ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
            zipOut.putNextEntry(zipEntry);

            final byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
            fis.close();
        }
        zipOut.close();
        fos.close();
    }
}