package com.baeldung.curltojava;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author OANJOLA
 */
public class JavaCurlClient {

    private final String USER_AGENT = "JavaCurlClient";

    public static void main(String[] args) throws Exception {
        JavaCurlClient client = new JavaCurlClient();
        
        // If proxy is used in JVM environment, set it up here
        //System.setProperty("http.proxyHost", "proxy");
        //System.setProperty("http.proxyPort", "9090");
        //System.setProperty("https.proxyHost", "proxy");
        //System.setProperty("https.proxyPort", "9090");
        
       
        String url = "https://postman-echo.com/get?foo1=bar1&foo2=bar2";
        System.out.println("Test HTTP GET request");
        HttpURLConnection httpURLConnection = client.sendGet(url);
        System.out.println(inputStreamToString(httpURLConnection.getInputStream()));

        System.out.println("Test HTTP POST request");
        Map postParams = new HashMap();
        postParams.put("param1", "param1Value");
        postParams.put("param2", "param2Value");
        postParams.put("param3", "param3Value");
        postParams.put("param4", "param4Value");
        
        url = "https://postman-echo.com/post";
        httpURLConnection = client.sendPost(url, postParams);
        System.out.println(inputStreamToString(httpURLConnection.getInputStream()));
        
        // Releasing resources
        client.releaseResource(httpURLConnection);
        
        System.out.println("Test GET with command line request");
        String curlCommand = "curl --location --request GET https://postman-echo.com/get?foo1=bar1&foo2=bar2";

        Process process = client.commandLine(curlCommand);
        System.out.println("Output value: " + inputStreamToString(process.getInputStream()));
        System.out.println("Error value: " + inputStreamToString(process.getErrorStream()));
        System.out.println("exit status: " + process.exitValue());

        System.out.println("Test POST with command line request");
        curlCommand = "curl --location --request POST https://postman-echo.com/post --data \"param3=param3Value&param4=param4Value&param1=param1Value&param2=param2Value\"";
        process = client.commandLine(curlCommand);
        System.out.println("Output value: " + inputStreamToString(process.getInputStream()));
        System.out.println("Error value: " + inputStreamToString(process.getErrorStream()));
        System.out.println("exit status: " + process.exitValue());
       
    }

    // HTTP GET request
    private HttpURLConnection sendGet(String url, String contentType) throws Exception {
        URL urlObj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
        connection.setDoOutput(true);
        connection.setInstanceFollowRedirects(false);
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", contentType);
        connection.setRequestProperty("charset", "utf-8");
        connection.setRequestProperty("User-Agent", USER_AGENT);
        connection.connect();
        return connection;
    }

    private HttpURLConnection sendGet(String url) throws Exception {
        return sendGet(url, "text/plain");
    }

    // HTTP POST request
    private HttpURLConnection sendPost(String url, Map<String, Object> postData) throws Exception {
        return sendPost(url, postData, "text/plain");
    }

    private HttpURLConnection sendPost(String url, Map<String, Object> postData, String contentType) throws Exception {
        byte[] byteData = httpBuildQueryString(postData);
        int postDataLength = byteData.length;
        URL urlObj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
        connection.setDoOutput(true);
        connection.setInstanceFollowRedirects(false);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", contentType);
        connection.setRequestProperty("charset", "utf-8");
        connection.setRequestProperty("Content-Length", Integer.toString(postDataLength));
        connection.setRequestProperty("User-Agent", USER_AGENT);
        connection.setUseCaches(false);
        connection.connect();
        try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
            wr.write(byteData);
        }
        return connection;
    }

    private void releaseResource(HttpURLConnection httpURLConnection) {
        httpURLConnection.disconnect();
    }

    private void releaseResource(Process process) {
        process.destroy();
    }

    public static byte[] httpBuildQueryString(Map<String, Object> postData) throws UnsupportedEncodingException {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Object> param : postData.entrySet()) {
            if (builder.length() != 0) {
                builder.append('&');
            }

            Object value = param.getValue();
            String key = param.getKey();

            if (value instanceof Object[] || value instanceof List<?>) {
                int size = value instanceof Object[] ? ((Object[]) value).length : ((List<?>) value).size();
                for (int i = 0; i < size; i++) {
                    Object val = value instanceof Object[] ? ((Object[]) value)[i] : ((List<?>) value).get(i);
                    if (i > 0) {
                        builder.append('&');
                    }
                    builder.append(URLEncoder.encode(key + "[" + i + "]", "UTF-8"));
                    builder.append('=');
                    builder.append(URLEncoder.encode(String.valueOf(val), "UTF-8"));
                }
            } else {
                builder.append(URLEncoder.encode(key, "UTF-8"));
                builder.append('=');
                builder.append(URLEncoder.encode(String.valueOf(value), "UTF-8"));
            }
        }
        return builder.toString().getBytes("UTF-8");
    }

    private static String inputStreamToString(InputStream inputStream) {
        final int bufferSize = 8 * 1024;
        byte[] buffer = new byte[bufferSize];
        final StringBuilder builder = new StringBuilder();
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, bufferSize)) {
            while (bufferedInputStream.read(buffer) != -1) {
                builder.append(new String(buffer));
            }
        } catch (IOException ex) {
            Logger.getLogger(JavaCurlClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return builder.toString();
    }

    private Process commandLine(String curlCommand) throws Exception {
        Process process = null;
        String command = curlCommand.replaceAll(",","").replaceAll("\"","");
        ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
        return processBuilder.start();
    }

}
