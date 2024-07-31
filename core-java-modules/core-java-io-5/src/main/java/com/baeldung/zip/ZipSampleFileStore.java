package com.baeldung.zip;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;

public class ZipSampleFileStore {

    public static final String ENTRY_NAME_PATTERN = "str-data-%s.txt";
    private static final Charset DEFAULT_ENCODING = StandardCharsets.UTF_8;

    private final File file;
    private final List<String> dataList;

    public ZipSampleFileStore(int numOfFiles, int fileSize) throws IOException {

        dataList = new ArrayList<>(numOfFiles);
        file = File.createTempFile("zip-sample", "");

        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(file))) {

            for (int idx=0; idx<=numOfFiles; idx++) {

                byte[] data = createRandomStringInByte(fileSize);
                dataList.add(new String(data, DEFAULT_ENCODING));

                ZipEntry entry = new ZipEntry(String.format(ENTRY_NAME_PATTERN, idx));
                zos.putNextEntry(entry);
                zos.write(data);
                zos.closeEntry();
            }
        }
    }

    public static byte[] createRandomStringInByte(int size) {
        Random random = new Random();
        byte[] data = new byte[size];
        for (int n = 0; n < data.length; n++) {
            char randomChar;
            int choice = random.nextInt(2); // 0 for uppercase, 1 for lowercase
            if (choice == 0) {
                randomChar = (char) ('A' + random.nextInt(26)); // 'A' to 'Z'
            } else {
                randomChar = (char) ('a' + random.nextInt(26)); // 'a' to 'z'
            }
            data[n] = (byte) randomChar;
        }
        return data;
    }

    public File getFile() {
        return file;
    }

    public List<String> getDataList() {
        return dataList;
    }

    public static String getString(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        IOUtils.copy(inputStream, byteArrayOutputStream);
        return byteArrayOutputStream.toString(DEFAULT_ENCODING);
    }

}