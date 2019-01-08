package com.baeldung.curltojava;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaCurlExamples {

    public static String inputStreamToString(InputStream inputStream) {
        final int bufferSize = 8 * 1024;
        byte[] buffer = new byte[bufferSize];
        final StringBuilder builder = new StringBuilder();
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, bufferSize)) {
            while (bufferedInputStream.read(buffer) != -1) {
                builder.append(new String(buffer));
            }
        } catch (IOException ex) {
            Logger.getLogger(JavaCurlExamples.class.getName()).log(Level.SEVERE, null, ex);
        }
        return builder.toString();
    }

    public static void consumeInputStream(InputStream inputStream) {
        inputStreamToString(inputStream);
    }
    
    public static void main(String[] args) {
        try {
            // Test GET command
            getCommand1();
            // Test reuse processBuilder instance with GET command
            getCommand2();
            // Test POST command
            postCommand();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public static void getCommand1() throws IOException {
        String command = "curl -X GET https://postman-echo.com/get?foo1=bar1&foo2=bar2";
        ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
        processBuilder.directory(new File("/home/"));
        Process process = processBuilder.start();
        InputStream inputStream = process.getInputStream();
        // Consume the inputStream so the process can exit
        JavaCurlExamples.consumeInputStream(inputStream);
        int exitCode = process.exitValue();
        
        assert (0 == exitCode);
    }
    
    public static void getCommand2() throws IOException {
        String command = "curl -X GET https://postman-echo.com/get?foo1=bar1&foo2=bar2";
        ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
        processBuilder.directory(new File("/home/"));
        Process process = processBuilder.start();
        
        // Re-use processBuilder
        processBuilder.command(new String[]{"newCommand", "arguments"});
        
        assert (true == process.isAlive());
    }

    public static void postCommand() throws IOException {
        String command = "curl -X POST https://postman-echo.com/post --data foo1=bar1&foo2=bar2";
        Process process = Runtime.getRuntime().exec(command);
        
        // Get the POST result
        String content = JavaCurlExamples.inputStreamToString(process.getInputStream());

        assert (null != content && !content.isEmpty());
    }

}
