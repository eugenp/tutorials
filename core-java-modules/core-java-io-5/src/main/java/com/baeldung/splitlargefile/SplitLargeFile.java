package com.baeldung.splitlargefile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;

class SplitLargeFile {

    public List<File> splitByFileSize(File largeFile, int maxSizeOfSplitFiles,
            String splitedFileDirPath) throws IOException {
        List<File> listOfSplitFiles = new ArrayList<>();
        try (InputStream in = Files.newInputStream(largeFile.toPath())) {
            final byte[] buffer = new byte[maxSizeOfSplitFiles];
            int dataRead = in.read(buffer);
            while (dataRead > -1) {
                File splitFile = getSplitFile(FilenameUtils.removeExtension(largeFile.getName()),
                        buffer, dataRead, splitedFileDirPath);
                listOfSplitFiles.add(splitFile);
                dataRead = in.read(buffer);
            }
        }
        return listOfSplitFiles;
    }

    private File getSplitFile(String largeFileName, byte[] buffer, int length,
            String splitedFileDirPath) throws IOException {
        File splitFile = File.createTempFile(largeFileName + "-", "-split",
                new File(splitedFileDirPath));
        try (FileOutputStream fos = new FileOutputStream(splitFile)) {
            fos.write(buffer, 0, length);
        }
        return splitFile;
    }

    public List<File> splitByNumberOfFiles(File largeFile, int noOfFiles, String splitedFileDirPath)
            throws IOException {
        return splitByFileSize(largeFile, getSizeInBytes(largeFile.length(), noOfFiles),
                splitedFileDirPath);
    }

    private int getSizeInBytes(long largefileSizeInBytes, int numberOfFilesforSplit) {
        if (largefileSizeInBytes % numberOfFilesforSplit != 0) {
            largefileSizeInBytes = ((largefileSizeInBytes / numberOfFilesforSplit) + 1)
                    * numberOfFilesforSplit;
        }
        long x = largefileSizeInBytes / numberOfFilesforSplit;
        if (x > Integer.MAX_VALUE) {
            throw new NumberFormatException("size too large");

        }
        return (int) x;
    }
}
