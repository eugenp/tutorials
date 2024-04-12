package com.baeldung.curltojava;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;

public class JavaCurlExamplesLiveTest {

    @Test
    public void givenCommand_whenCalled_thenProduceZeroExitCode() throws IOException {
        String command = "curl -X GET https://postman-echo.com/get?foo1=bar1&foo2=bar2";
        ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
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
        String command = "curl -X GET https://postman-echo.com/get?foo1=bar1&foo2=bar2";
        ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
        processBuilder.directory(new File("/home/"));
        Process process = processBuilder.start();
        
        // Re-use processBuilder
        processBuilder.command(new String[]{"newCommand", "arguments"});
        
        Assert.assertEquals(true, process.isAlive());
    }

    @Test
    public void whenRequestPost_thenCheckIfReturnContent() throws IOException {
        String command = "curl -X POST https://postman-echo.com/post --data foo1=bar1&foo2=bar2";
        Process process = Runtime.getRuntime().exec(command);
        
        // Get the POST result
        String content = JavaCurlExamples.inputStreamToString(process.getInputStream());

        Assert.assertTrue(null != content && !content.isEmpty());
    }

}
