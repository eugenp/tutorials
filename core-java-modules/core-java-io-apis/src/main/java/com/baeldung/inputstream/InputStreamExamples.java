package com.baeldung.inputstream;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class InputStreamExamples{

    public static void readWithFileInputStream() throws IOException {
        String fileNameWithPath = "core-java-modules/core-java-io-apis/src/main/resources/issample.txt";
        InputStream inputStream = new FileInputStream(fileNameWithPath);
        printStream(inputStream);
    }
    public static void readWithByteArrayInputStream() throws IOException {
        byte[] byteArray = "In this example, we use btye array input stream".getBytes(StandardCharsets.UTF_8);
        InputStream inputStream = new ByteArrayInputStream(byteArray);
        printStream(inputStream);
    }


    public static void readWithObjectInputStream() throws IOException, ClassNotFoundException {
        //Serialize a Hashmap then write it into a file.
        String path = "core-java-modules/core-java-io-apis/src/main/resources/sample-out.txt";
        FileOutputStream fileOutStream = new FileOutputStream( path);
        ObjectOutputStream objectOutStream
            = new ObjectOutputStream(fileOutStream);

        Map<String, String> kv = new HashMap<>();
        kv.put("baseURL", "baeldung.com");
        kv.put("apiKey", "this_is_a_test_key");
        objectOutStream.writeObject(kv);
        objectOutStream.close();

        //Deserialize the contents of a file then transform it back into a Hashmap
        FileInputStream fileStream = new FileInputStream(path);
        ObjectInputStream input = new ObjectInputStream(fileStream);
        HashMap<String,String> inputKv = (HashMap<String, String>) input.readObject();
        System.out.println(inputKv); //{baseURL=baeldung.com, apiKey=this_is_a_test_key}
    }

    public static void printStream(InputStream inputStream) throws IOException {
        int byteData = 0;
        byte[] array = new byte[100];
        while(byteData > -1){
            byteData = inputStream.read(array);
            System.out.println(new String(array));
        }
    }
    public static void main(String args[]){
        try {
            readWithFileInputStream();
            readWithByteArrayInputStream();
            readWithObjectInputStream();

        } catch (Exception e) {

           e.printStackTrace();
        }
    }
}
