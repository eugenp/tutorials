package com.baeldung.jexcel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import jxl.read.biff.BiffException;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

import com.baeldung.jexcel.JExcelHelper;

import jxl.write.WriteException;
import jxl.read.biff.BiffException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

public class JExcelIntegrationTest {

    private JExcelHelper jExcelHelper;
    private static String FILE_NAME = "temp.xls";
    private String fileLocation;

    @Before
    public void generateExcelFile() throws IOException, WriteException {

        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        fileLocation = path.substring(0, path.length() - 1) + FILE_NAME;

        jExcelHelper = new JExcelHelper();
        jExcelHelper.writeJExcel();

    }

    @Test
    public void whenParsingJExcelFile_thenCorrect() throws IOException, BiffException {
        Map<Integer, List<String>> data = jExcelHelper.readJExcel(fileLocation);

        assertEquals("Name", data.get(0)
            .get(0));
        assertEquals("Age", data.get(0)
            .get(1));

        assertEquals("John Smith", data.get(2)
            .get(0));
        assertEquals("20", data.get(2)
            .get(1));
    }

    @After
    public void cleanup(){
        File testFile = new File(fileLocation);
        if (testFile.exists()) {
           testFile.delete();     
        }
    }
}