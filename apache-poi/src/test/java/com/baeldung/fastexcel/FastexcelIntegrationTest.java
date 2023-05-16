package com.baeldung.fastexcel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class FastexcelIntegrationTest {

    private FastexcelHelper fastexcelHelper;
    private static String FILE_NAME = "fastexcel.xlsx";
    private String fileLocation;

    @Before
    public void generateExcelFile() throws IOException {

        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        fileLocation = path.substring(0, path.length() - 1) + FILE_NAME;

        fastexcelHelper = new FastexcelHelper();
        fastexcelHelper.writeExcel();
    }

    @Test
    public void whenParsingExcelFile_thenCorrect() throws IOException {
        Map<Integer, List<String>> data = fastexcelHelper.readExcel(fileLocation);

        assertEquals("Name", data.get(1).get(0));
        assertEquals("Age", data.get(1).get(1));

        assertEquals("John Smith", data.get(3).get(0));
        assertEquals("20", data.get(3).get(1));
    }

    @After
    public void cleanup() {
        File testFile = new File(fileLocation);
        if (testFile.exists()) {
            testFile.delete();
        }
    }
}
