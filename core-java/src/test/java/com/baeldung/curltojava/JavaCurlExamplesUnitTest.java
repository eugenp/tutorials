package com.baeldung.curltojava;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Assert;
import org.junit.Test;

public class JavaCurlExamplesUnitTest {


    @Test
    public void givenCommand_whenCalled_thenProduceZeroExitCode() throws IOException {
        String command = "curl --location --request GET \"https://postman-echo.com/get?foo1=bar1&foo2=bar2\"";
        ProcessBuilder processBuilder = new ProcessBuilder(command.replaceAll("\"", "").split(" "));
        processBuilder.directory(new File("/home/"));
        Process process = processBuilder.start();
        InputStream inputStream = process.getInputStream();
        // Consume the inputStream so the process can exit
        JavaCurlExamples.consumeInputStream(inputStream);
        int exitCode = process.exitValue();
        
        Assert.assertEquals(0, exitCode);
    }
    
    @Test
    public void givenNewCommands_whenCalled_thenCheckIfIsAlive() throws IOException {
        String command = "curl --location --request GET \"https://postman-echo.com/get?foo1=bar1&foo2=bar2\"";
        ProcessBuilder processBuilder = new ProcessBuilder(command.replaceAll("\"", "").split(" "));
        processBuilder.directory(new File("/home/"));
        Process process = processBuilder.start();
        
        // Re-use processBuilder
        processBuilder.command(new String[]{"newCommand", "arguments"});
        
        Assert.assertEquals(true, process.isAlive());
    }

    @Test
    public void whenRequestGet_thenReturnSuccessResponseCode() throws IOException {
        String url = "https://postman-echo.com/get?foo1=bar1&foo2=bar2";
        URL urlObj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
        connection.setDoOutput(true);
        connection.setInstanceFollowRedirects(false);
        connection.setRequestMethod("GET");
        connection.connect();
        
        Assert.assertEquals(HttpURLConnection.HTTP_OK, connection.getResponseCode());
    }

}
