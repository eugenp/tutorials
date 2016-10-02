package com.baeldung.file;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import static org.hamcrest.CoreMatchers.containsString;

public class FileOperationsTest {

    @Test
    public void givenFileName_whenUsingClassloader_thenFileData() throws IOException {
        String expectedData = "Hello World from fileTest.txt!!!";

        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("fileTest.txt");
        String data = readFromInputStream(inputStream);

        Assert.assertThat(data, containsString(expectedData));
}

    @Test
    public void givenFileNameAsAbsolutePath_whenUsingClasspath_thenFileData() throws IOException {
        String expectedData = "Hello World from fileTest.txt!!!";

        Class clazz = FileOperationsTest.class;
        InputStream inputStream = clazz.getResourceAsStream("/fileTest.txt");
        String data = readFromInputStream(inputStream);

        Assert.assertThat(data, containsString(expectedData));
    }

    @Test
    public void givenURLName_whenUsingURL_thenFileData() throws IOException {
        String expectedData = "Baeldung";

        URL urlObject = new URL("http://www.baeldung.com/");
        URLConnection urlConnection = urlObject.openConnection();

        InputStream inputStream = urlConnection.getInputStream();
        String data = readFromInputStream(inputStream);

        Assert.assertThat(data, containsString(expectedData));
    }

    @Test
    public void givenFileName_whenUsingJarFile_thenFileData() throws IOException {
        String expectedData = "BSD License";

        Class clazz = Matchers.class;
        InputStream inputStream = clazz.getResourceAsStream("/LICENSE.txt");
        String data = readFromInputStream(inputStream);

        Assert.assertThat(data, containsString(expectedData));
    }

    private String readFromInputStream(InputStream inputStream) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder resultStringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            resultStringBuilder.append(line);
            resultStringBuilder.append("\n");
        }
        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();
        return resultStringBuilder.toString();
    }
}