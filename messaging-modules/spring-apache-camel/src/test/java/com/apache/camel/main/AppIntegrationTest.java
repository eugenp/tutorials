package com.apache.camel.main;

import com.baeldung.camel.apache.main.App;
import junit.framework.TestCase;
import org.apache.camel.util.FileUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AppIntegrationTest extends TestCase {

    private static final String FILE_NAME = "file.txt";
    private static final String SAMPLE_INPUT_DIR = "src/test/data/sampleInputFile/";
    private static final String TEST_INPUT_DIR = "src/test/data/input/";
    private static final String UPPERCASE_OUTPUT_DIR = "src/test/data/outputUpperCase/";
    private static final String LOWERCASE_OUTPUT_DIR = "src/test/data/outputLowerCase/";

    @Before
    public void setUp() throws Exception {
        // Prepare input file for test
        copySampleFileToInputDirectory();
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Deleting the test input and output files...");
        deleteFile(TEST_INPUT_DIR);
        deleteFile(LOWERCASE_OUTPUT_DIR);
        deleteFile(UPPERCASE_OUTPUT_DIR);
    }

    @Test
    public final void testMain() throws Exception {
        App.main(null);

        String inputFileContent = readFileContent(SAMPLE_INPUT_DIR + FILE_NAME);
        String outputUpperCase = readFileContent(UPPERCASE_OUTPUT_DIR + FILE_NAME);
        String outputLowerCase = readFileContent(LOWERCASE_OUTPUT_DIR + FILE_NAME);

        System.out.println("Input File content = [" + inputFileContent + "]");
        System.out.println("UpperCaseOutput file content = [" + outputUpperCase + "]");
        System.out.println("LowerCaseOtput file content = [" + outputLowerCase + "]");

        assertEquals(inputFileContent.toUpperCase(), outputUpperCase);
        assertEquals(inputFileContent.toLowerCase(), outputLowerCase);
    }

    private String readFileContent(String path) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded);
    }

    private void deleteFile(String path) {
        try {
            FileUtil.removeDir(new File(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Copy sample input file to input directory.
     */
    private void copySampleFileToInputDirectory() {
        File sourceFile = new File(SAMPLE_INPUT_DIR + FILE_NAME);
        File destFile = new File(TEST_INPUT_DIR + FILE_NAME);

        if (!sourceFile.exists()) {
            System.out.println("Sample input file not found at location = [" + SAMPLE_INPUT_DIR + FILE_NAME + "]. Please provide this file.");
        }

        if (!destFile.exists()) {
            try {
                System.out.println("Creating input file = [" + TEST_INPUT_DIR + FILE_NAME + "]");

                File destDir = new File(TEST_INPUT_DIR);
                if (!destDir.exists()) {
                    destDir.mkdir();
                }
                destFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileChannel source = null;
        FileChannel destination = null;

        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            if (destination != null && source != null) {
                destination.transferFrom(source, 0, source.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                source.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                destination.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
