//package com.baeldung;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.URISyntaxException;
//import java.net.URL;
//import java.net.URLConnection;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.nio.file.StandardOpenOption;
//import java.util.ArrayList;
//import java.util.Map;
//import java.util.Random;
//import java.util.Scanner;
//
//import org.junit.FixMethodOrder;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.JUnit4;
//import org.junit.runners.MethodSorters;
//
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//@RunWith(JUnit4.class)
//public class MemoryLeaksTest {
//    private Random random = new Random();
//    public static final ArrayList<Double> list = new ArrayList<Double>(1000000);
//
//    @Test
//    public void givenStaticField_whenLotsOfOperations_thenMemoryLeak() throws InterruptedException {
//        for (int i = 0; i < 1000000; i++) {
//            list.add(random.nextDouble());
//        }
//        System.gc();
//        Thread.sleep(10000); //to allow GC do its job
//    }
//
//    
//    @Test
//    public void givenNormalField_whenLotsOfOperations_thenGCWorksFine() throws InterruptedException {
//        addElementsToTheList();
//        System.gc();
//        Thread.sleep(10000); //to allow GC do its job
//    }
//    
//    private void addElementsToTheList(){
//        ArrayList<Double> list = new ArrayList<Double>(1000000);
//        for (int i = 0; i < 1000000; i++) {
//            list.add(random.nextDouble());
//        }
//    }
//
//    @SuppressWarnings({ "resource" })
//    @Test(expected = OutOfMemoryError.class)
//    public void givenLengthString_whenIntern_thenOutOfMemory() throws IOException, InterruptedException {
//        Thread.sleep(15000);
//        String str = new Scanner(new File("src/test/resources/large.txt"), "UTF-8").useDelimiter("\\A")
//            .next();
//        System.gc();
//        str.intern();
//        Thread.sleep(10000);
//    }
//
//    @Test(expected = OutOfMemoryError.class)
//    public void givenURL_whenUnclosedStream_thenOutOfMemory() throws IOException, URISyntaxException {
//        String str = "";
//        URLConnection conn = new URL("http:norvig.com/big.txt").openConnection();
//        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
//        while (br.readLine() != null) {
//            str += br.readLine();
//        }
//        Files.write(Paths.get("src/main/resources/"), str.getBytes(), StandardOpenOption.CREATE);
//    }
//
//    @SuppressWarnings("unused")
//    @Test(expected = OutOfMemoryError.class)
//    public void givenConnection_whenUnclosed_thenOutOfMemory() throws IOException, URISyntaxException {
//        URL url = new URL("ftp:speedtest.tele2.net");
//        URLConnection urlc = url.openConnection();
//        InputStream is = urlc.getInputStream();
//        String str = "";
//        while (true) {
//            str += is.toString()
//                .intern();
//        }
//    }
//
//    @Test(expected = OutOfMemoryError.class)
//    public void givenMap_whenNoEqualsNoHashCodeMethods_thenOutOfMemory() throws IOException, URISyntaxException {
//        Map<Object, Object> map = System.getProperties();
//        while (true) {
//            map.put(new Key("key"), "value");
//        }
//    }
//}
