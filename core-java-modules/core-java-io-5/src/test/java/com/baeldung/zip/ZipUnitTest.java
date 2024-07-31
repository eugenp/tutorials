package com.baeldung.zip;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.*;
import java.util.*;
import java.util.zip.*;

import org.junit.*;

public class ZipUnitTest {

    private static File compressedFile;
    private static List<String> dataList = new ArrayList<>();

    @BeforeClass
    public static void prepareData() throws IOException {
        ZipSampleFileStore sampleFileStore = new ZipSampleFileStore(ZipBenchmark.NUM_OF_FILES, ZipBenchmark.DATA_SIZE);
        compressedFile = sampleFileStore.getFile();
        dataList = sampleFileStore.getDataList();
    }

    @Test
    public void whenCreateZipFile_thenCompressedSizeShouldBeLessThanOriginal() throws IOException {
        byte[] data = ZipSampleFileStore.createRandomStringInByte(10240);
        File file = File.createTempFile("zip-temp", "");

        try (
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            ZipOutputStream zos = new ZipOutputStream(bos)
        ) {
            ZipEntry zipEntry = new ZipEntry("zip-entry.txt");
            zos.putNextEntry(zipEntry);
            zos.write(data);
            zos.closeEntry();

            assertThat(file.length()).isLessThan(data.length);
        }
        finally {
            file.delete();
        }
    }

    @Test
    public void whenReadAllEntriesViaZipFile_thenDataIsEqualtoTheSource() throws IOException {

        try (ZipFile zipFile = new ZipFile(compressedFile)) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            List<? extends ZipEntry> entryList = Collections.list(entries);

            for (int idx=0; idx<entryList.size(); idx++) {
                ZipEntry zipEntry = entryList.get(idx);
                try (InputStream inputStream = zipFile.getInputStream(zipEntry)) {
                    String actual = ZipSampleFileStore.getString(inputStream);
                    assertThat(actual).as("Data for ZIP entry: " + zipEntry.getName()).isEqualTo(dataList.get(idx));
                }
            }
        }
    }

    @Test
    public void whenReadAllEntriesViaZipInputStream_thenDataIsEqualtoTheSource() throws IOException {
        int idx = 0;
        try (
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(compressedFile));
            ZipInputStream zipInputStream = new ZipInputStream(bis)
        ) {
            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                String actual = ZipSampleFileStore.getString(zipInputStream);
                assertThat(actual).as("Data for ZIP entry: " + zipEntry.getName()).isEqualTo(dataList.get(idx++));
            }
        }
    }

    @AfterClass
    public static void cleanup() {
        if (compressedFile.exists()) {
            compressedFile.delete();
        }
    }

}