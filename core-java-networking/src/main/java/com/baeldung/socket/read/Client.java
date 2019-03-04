package com.baeldung.socket.read;

import java.net.*; 
import java.io.*; 

public class Client {

    //Initialize socket, input and output stream
    private Socket socket = null;
    private DataInputStream in = null;
    private DataOutputStream out = null;
    
    public void runClient(String ip, int port) {
        try {
            socket = new Socket(ip, port);
            System.out.println("Connected to server ...");
            in = new DataInputStream(System.in);
            out = new DataOutputStream(socket.getOutputStream());
        } catch(Exception e) {
            e.printStackTrace();
        }
        char type = 's'; // s for string
        int length = 29;
        String data = "This is a string of length 29";
        byte[] dataInBytes = data.getBytes();         
        //Sending data in TLV format        
        try {
            out.writeChar(type);
            out.writeInt(length);
            out.write(dataInBytes);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}