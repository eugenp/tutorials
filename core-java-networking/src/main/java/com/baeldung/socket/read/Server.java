package com.baeldung.socket.read;

import java.net.*; 
import java.io.*; 

public class Server {

    //Socket and input stream
    private Socket socket = null;
    private ServerSocket server = null;
    private DataInputStream in = null;
    
    public void runServer(int port) {
        //Start the server and wait for connection
        try {
            server = new ServerSocket(port);
            System.out.println("Server Started. Waiting for connection ...");
            socket = server.accept();
            System.out.println("Got connection from client.");
            //Get input stream from socket variable and convert the same to DataInputStream
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            //Read type and length of data
            char dataType = in.readChar();
            int length = in.readInt();
            System.out.println("Type : "+dataType);
            System.out.println("Lenght :"+length);
            //Read String data in bytes
            byte[] messageByte = new byte[length];
            boolean end = false;
            String dataString = "";
            int totalBytesRead = 0;
            //We need to run while loop, to read all data in that stream
            while(!end) {
                int currentBytesRead = in.read(messageByte);
                totalBytesRead = currentBytesRead + totalBytesRead;
                if(totalBytesRead <= length) {
                    dataString += new String(messageByte,0,currentBytesRead);   
                } else {
                    dataString += new String(messageByte,0,length - totalBytesRead + currentBytesRead); 
                }
                if(dataString.length()>=length) {
                    end = true;
                }
            }
            System.out.println("Read "+length+" bytes of message from client. Message = "+dataString);;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}