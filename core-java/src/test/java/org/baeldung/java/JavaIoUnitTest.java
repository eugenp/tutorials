package org.baeldung.java;

import java.io.*;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Scanner;

import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

/**
 * java io测试
 */
@Ignore("need large file for testing")
public class JavaIoUnitTest {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected String path = "/Users/zn.wang/work_tools/isb_git_workspaces/tutorials/core-java/src/test/resources/API_file.pdf";
    // tests - iterate lines in a file

    /**
     * @see com.google.common.io.Files#readLines(File file, Charset charset)
     * @throws IOException
     */
    @Test
    public final void givenUsingGuava_whenIteratingAFile_thenCorrect() throws IOException {
        // final String path = "G:\\full\\train\\input\\" + "trainDataNegative.csv";
        // final String path = "G:\\full\\train\\input\\" + "trainDataPositive.csv";

        logMemory();
        Files.readLines(new File(path), Charsets.UTF_8);
        logMemory();
    }

    /**
     * @see org.apache.commons.io.FileUtils#readLines(File file)
     * @throws IOException
     */
    @Test
    public final void givenUsingCommonsIo_whenIteratingAFileInMemory_thenCorrect() throws IOException {
        // final String path = "G:\\full\\train\\input\\" + "trainDataNegative.csv";
        //final String path = "G:\\full\\train\\input\\" + "trainDataPositive.csv";

        logMemory();
        FileUtils.readLines(new File(path));
        logMemory();
    }

    /**
     * @see java.io.FileInputStream
     * @throws IOException
     */
    @Test
    public final void whenStreamingThroughAFile_thenCorrect() throws IOException {
        // final String path = "G:\\full\\train\\input\\" + "trainDataNegative.csv";
        // final String path = "G:\\full\\train\\input\\" + "trainDataPositive.csv";
        path = "/Users/zn.wang/work_tools/isb_git_workspaces/tutorials/core-java/src/test/resources/readfile.text";

        logMemory();

        FileInputStream inputStream = null;
        Scanner sc = null;
        List<String> inputContentLines = Lists.newArrayList();
        try {
            inputStream = new FileInputStream(path);
            sc = new Scanner(inputStream, "UTF-8");
            while (sc.hasNextLine()) {
                final String line = sc.nextLine();
                inputContentLines.add(line + "\n");
            }
            // note that Scanner suppresses exceptions
            if (sc.ioException() != null) {
                throw sc.ioException();
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (sc != null) {
                sc.close();
            }
        }

        logMemory();

        //==========下面是捎带着测试写文件===============
        final String writeFile = "/Users/zn.wang/work_tools/isb_git_workspaces/tutorials/core-java/src/test/resources/writefile.text";
        writeFileUnitTools(inputContentLines , writeFile);
    }

    public static void writeFileUnitTools(List<String> contentLines , String writeFilePath){
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(writeFilePath);
            for(String line : contentLines){
                outputStream.write(line.getBytes());
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                outputStream.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @see org.apache.commons.io.FileUtils#lineIterator( File file, String encoding)
     * @throws IOException
     */
    @Test
    public final void givenUsingApacheIo_whenStreamingThroughAFile_thenCorrect() throws IOException {
        // final String path = "G:\\full\\train\\input\\" + "trainDataNegative.csv";
        // final String path = "G:\\full\\train\\input\\" + "trainDataPositive.csv";
        path = "/Users/zn.wang/work_tools/isb_git_workspaces/tutorials/core-java/src/test/resources/readfile.text";

        logMemory();

        final LineIterator it = FileUtils.lineIterator(new File(path), "UTF-8");
        try {
            while (it.hasNext()) {
                final String line = it.nextLine();
                // do something with line
                System.out.println(line);
            }
        } finally {
            LineIterator.closeQuietly(it);
        }

        logMemory();
    }

    // utils

    private final void logMemory() {
        logger.info("Max Memory: {} Mb", Runtime.getRuntime()
            .maxMemory() / 1048576);
        logger.info("Total Memory: {} Mb", Runtime.getRuntime()
            .totalMemory() / 1048576);
        logger.info("Free Memory: {} Mb", Runtime.getRuntime()
            .freeMemory() / 1048576);
    }

}
