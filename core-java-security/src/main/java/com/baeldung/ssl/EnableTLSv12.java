package com.baeldung.ssl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.security.SecureRandom;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class EnableTLSv12 {

    public String url = "";
    public Integer port = null;

    public EnableTLSv12() {
    }

    public static void main(String[] args) {
        try {
            EnableTLSv12 enableTLSv12 = new EnableTLSv12();
            if (args.length != 2) {
                System.out.println("Provide the server url and the secure port:");
                System.exit(-1);
            }
            enableTLSv12.getHost(args);
            enableTLSv12.getPort(args);
            enableTLSv12.enableTLSv12UsingHttpConnection();
            enableTLSv12.enableTLSv12UsingProtocol();
            enableTLSv12.enableTLSv12UsingSSLContext();
            enableTLSv12.enableTLSv12UsingSSLParameters();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getPort(String[] args) {
        url = args[0];
    }

    private void getHost(String[] args) {
        String portNumber = args[1];
        port = Integer.parseInt(portNumber);
    }

    private void handleCommunication(SSLSocket socket, String usedTLSProcess) throws IOException {
        System.out.print("Enabled TLS v1.2 on " + usedTLSProcess);
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
        out.println("GET / HTTP/1.0");
        out.println();
        out.flush();
        if (out.checkError())
            System.out.println("SSLSocketClient:  java.io.PrintWriter error");

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null)
            System.out.println(inputLine);

        in.close();
        out.close();
        socket.close();
    }

    public void enableTLSv12UsingSSLParameters() {
        try {
            SSLSocketFactory socketFac = (SSLSocketFactory) SSLSocketFactory.getDefault();
            SSLSocket sslSocket = (SSLSocket) socketFac.createSocket(url.trim(), port);
            SSLParameters params = new SSLParameters();
            params.setProtocols(new String[] { "TLSv1.2" });
            sslSocket.setSSLParameters(params);
            sslSocket.startHandshake();
            handleCommunication(sslSocket, "SSLSocketFactory-SSLParameters");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enableTLSv12UsingProtocol() throws IOException {
        try {
            SSLSocketFactory socketFac = (SSLSocketFactory) SSLSocketFactory.getDefault();
            SSLSocket sslSocket = (SSLSocket) socketFac.createSocket(url.trim(), port);
            sslSocket.setEnabledProtocols(new String[] { "TLSv1.2" });
            sslSocket.startHandshake();
            handleCommunication(sslSocket, "SSLSocketFactory-EnabledProtocols");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enableTLSv12UsingHttpConnection() {
        try {
            URL urls = new URL("https://" + url + ":" + port);
            SSLContext ssl = SSLContext.getInstance("TLSv1.2");
            ssl.init(null, null, new SecureRandom());
            HttpsURLConnection con = (HttpsURLConnection) urls.openConnection();
            con.setSSLSocketFactory(ssl.getSocketFactory());
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String input;

            while ((input = br.readLine()) != null) {
                System.out.println(input);
            }
            br.close();
            System.out.print("Created TLSv1.2 connection on HttpsURLConnection");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enableTLSv12UsingSSLContext() {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, null, new SecureRandom());
            SSLSocketFactory engine = sslContext.getSocketFactory();
            SSLSocket socket = (SSLSocket) engine.createSocket(url, port);
            handleCommunication(socket, "SSLContext");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
