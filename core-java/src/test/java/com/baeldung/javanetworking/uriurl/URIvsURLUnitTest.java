package com.baeldung.javanetworking.uriurl;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;

import com.google.common.io.CharStreams;
import org.apache.commons.io.IOUtils;
import static org.junit.Assert.*;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * 测试：URI和URL的区别
 */
public class URIvsURLUnitTest {

    @Test
    public void whenCreatingURIs_thenSameInfo() throws URISyntaxException {
        URI firstURI = new URI("somescheme://theuser:thepassword@someauthority:80/some/path?thequery#somefragment");
        URI secondURI = new URI("somescheme", "theuser:thepassword", "someuthority", 80, "/some/path", "thequery", "somefragment");

        assertEquals(firstURI.getScheme(), secondURI.getScheme());
        assertEquals(firstURI.getPath(), secondURI.getPath());
    }

    @Test
    public void whenCreatingURLs_thenSameInfo() throws MalformedURLException {
        URL firstURL = new URL("http://theuser:thepassword@somehost:80/path/to/file?thequery#somefragment");
        URL secondURL = new URL("http", "somehost", 80, "/path/to/file");

        System.out.println("firstURL:{}" + firstURL);
        System.out.println("secondURL:{}" + secondURL);

        assertEquals(firstURL.getHost(), secondURL.getHost());
        assertEquals(firstURL.getPath(), secondURL.getPath());
    }

    @Test
    public void whenCreatingURI_thenCorrect() {
        URI uri = URI.create("urn:isbn:1234567890");

        System.out.println("uri:{}" + uri);
        assertNotNull(uri);
    }

    @Test(expected = MalformedURLException.class)
    public void whenCreatingURLs_thenException() throws MalformedURLException {
        URL theURL = new URL("otherprotocol://somehost/path/to/file");

        assertNotNull(theURL);
    }

    @Test
    public void givenObjects_whenConverting_thenCorrect() throws MalformedURLException, URISyntaxException {
        String aURIString = "http://somehost:80/path?thequery";
        URI uri = new URI(aURIString);
        URL url = new URL(aURIString);
        System.out.println("uri:{}" + uri);
        System.out.println("url:{}" + url);

        URL toURL = uri.toURL();
        URI toURI = url.toURI();
        System.out.println("toURL:{}" + uri);
        System.out.println("toURI:{}" + uri);

        assertNotNull(url);
        assertNotNull(uri);
        assertEquals(toURL.toString(), toURI.toString());
    }

    @Test(expected = MalformedURLException.class)
    public void givenURI_whenConvertingToURL_thenException() throws MalformedURLException, URISyntaxException {
        URI uri = new URI("somescheme://someauthority/path?thequery");
        System.out.println("uri:{}" + uri);

        try{
            URL url = uri.toURL();
            System.out.println("url:{}" + url);
            assertNotNull(url);
        }
        catch (Throwable e){
            e.printStackTrace();
        }
    }

    @Test
    public void givenURL_whenGettingContents_thenCorrect() throws MalformedURLException, IOException {
        String theStringToParseAsAURL = "http://www.baidu.com";//使用原始的http://courses.baeldung.com可能看不到预期的结果，换成http://www.baidu.com查看结果
        URL url = new URL(theStringToParseAsAURL);
        System.out.println("url:{}" + url);
        try{
            InputStream inputStream = url.openStream();
            System.out.println("inputStream:{}" + inputStream);
            System.out.println("inputStream.available():{}" + inputStream.available());

            //String inputStreamToString_1 = CharStreams.toString(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            //System.out.println("inputStreamToString_1:{}" + inputStreamToString_1.toString());

            //String inputStreamToString_2 = IOUtils.toString(inputStream , Charset.forName("UTF-8"));
            //System.out.println("inputStreamToString_2:{}" + inputStreamToString_2);

            String inputStreamToString_3 = convert(inputStream , Charset.forName("UTF-8"));
            System.out.println("inputStreamToString_3:{}" + inputStreamToString_3);
        }
        catch (Throwable e){
            e.printStackTrace();
        }
    }

    /**
     * inputStream -> String
     * @param inputStream
     * @param charset
     * @return
     * @throws IOException
     */
    public String convert(InputStream inputStream, Charset charset) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();
        String line;

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, charset))) {

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }

        return stringBuilder.toString();
    }

    /**
     * 读取InputStream
     */
    @Test
    public void readInputStream() throws IOException {
        InputStream initialStream = new ByteArrayInputStream(new byte[] { 0, 1, 2 });
        System.out.println("initialStream.available()" + initialStream.available());

        byte[] targetArray = new byte[0];
        try {
            targetArray = new byte[initialStream.available()];
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        try {
            initialStream.read(targetArray);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("targetArray:{}" + targetArray.length);
        for(byte byteTemp : targetArray){
            System.out.println(byteTemp);
        }
    }

    /**
     * 读取InputStream
     */
    @Test
    public void nativeReadInputStream(){
        InputStream is = new ByteArrayInputStream(new byte[] { 0, 1, 2 });
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try {
            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                buffer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        byte[] byteArray = buffer.toByteArray();
        System.out.println("byteArray size:{}" + byteArray.length);
        for(byte byteTemp : byteArray){
            System.out.println(byteTemp);
        }
    }

}
