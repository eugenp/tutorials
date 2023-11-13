package com.baeldung.inputstreamreader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class InputStreamReaderExample {
    public static void readWithInputStreamReader() throws IOException {
        String path = "core-java-modules/core-java-io-apis/src/main/resources/issample.txt";
        FileInputStream fis = new FileInputStream(path);
        BufferedReader br
            = new BufferedReader(new InputStreamReader(fis, StandardCharsets.UTF_8));
        String ln;
        while((ln = br.readLine())!= null){
            System.out.println(ln); // prints Hello Baeldung!
        }
    }

}
