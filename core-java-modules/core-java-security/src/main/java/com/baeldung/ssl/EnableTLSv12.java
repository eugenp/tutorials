package com.baeldung.ssl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnableTLSv12 {

    private final Logger logger = LoggerFactory.getLogger(EnableTLSv12.class);

    public String url = "";
    public Integer port = null;

    public EnableTLSv12() {
    }

    public static void main(String[] args) throws IOException, KeyManagementException, NoSuchAlgorithmException {
        EnableTLSv12 enableTLSv12 = new EnableTLSv12();
        if (args.length != 2) {
            System.out.println("Provide the server url and the secure port:");
            System.exit(-1);
        }
        enableTLSv12.setHost(args);
        enableTLSv12.setPort(args);
        enableTLSv12.enableTLSv12UsingHttpConnection();
        enableTLSv12.enableTLSv12UsingProtocol();
        enableTLSv12.enableTLSv12UsingSSLContext();
        enableTLSv12.enableTLSv12UsingSSLParameters();
    }

    private void setPort(String[] args) {
        url = args[0];
    }

    private void setHost(String[] args) {
        String portNumber = args[1];
        port = Integer.parseInt(portNumber);
    }

    private void handleCommunication(SSLSocket socket, String usedTLSProcess) throws IOException {
        logger.debug("Enabled TLS v1.2 on " + usedTLSProcess);
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            out.println("GET / HTTP/1.0");
            out.println();
            out.flush();
            if (out.checkError()) {
                logger.error("SSLSocketClient:  java.io.PrintWriter error");
                return;
            }

            String inputLine;
            while ((inputLine = in.readLine()) != null)
                logger.info(inputLine);
        }
    }

    public void enableTLSv12UsingSSLParameters() throws UnknownHostException, IOException {
        SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket sslSocket = (SSLSocket) socketFactory.createSocket(url.trim(), port);
        SSLParameters params = new SSLParameters();
        params.setProtocols(new String[] { "TLSv1.2" });
        sslSocket.setSSLParameters(params);
        sslSocket.startHandshake();
        handleCommunication(sslSocket, "SSLSocketFactory-SSLParameters");
    }

    public void enableTLSv12UsingProtocol() throws IOException {
        SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket sslSocket = (SSLSocket) socketFactory.createSocket(url, port);
        sslSocket.setEnabledProtocols(new String[] { "TLSv1.2" });
        sslSocket.startHandshake();
        handleCommunication(sslSocket, "SSLSocketFactory-EnabledProtocols");
    }

    public void enableTLSv12UsingHttpConnection() throws IOException, NoSuchAlgorithmException, KeyManagementException {
        URL urls = new URL("https://" + url + ":" + port);
        SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
        sslContext.init(null, null, new SecureRandom());
        HttpsURLConnection connection = (HttpsURLConnection) urls.openConnection();
        connection.setSSLSocketFactory(sslContext.getSocketFactory());
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String input;
            while ((input = br.readLine()) != null) {
                logger.info(input);
            }
        }
        logger.debug("Created TLSv1.2 connection on HttpsURLConnection");
    }

    public void enableTLSv12UsingSSLContext() throws NoSuchAlgorithmException, KeyManagementException, UnknownHostException, IOException {
        SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
        sslContext.init(null, null, new SecureRandom());
        SSLSocketFactory socketFactory = sslContext.getSocketFactory();
        SSLSocket socket = (SSLSocket) socketFactory.createSocket(url, port);
        handleCommunication(socket, "SSLContext");
    }

}
