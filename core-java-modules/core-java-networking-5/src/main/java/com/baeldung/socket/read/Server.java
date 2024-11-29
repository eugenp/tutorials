package com.baeldung.socket.read;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Server {
    
    public void runServer(int port) {
        //Start the server and wait for connection
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("Server Started. Waiting for connection ...");
            Socket socket = server.accept();
            System.out.println("Got connection from client.");
            //Get input stream from socket variable and convert the same to DataInputStream
            DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            //Read type and length of data
            char dataType = in.readChar();
            int length = in.readInt();
            System.out.println("Type : "+dataType);
            System.out.println("Lenght :"+length);
            if(dataType == 's') {
                //Read String data in bytes
                byte[] messageByte = new byte[length];
                boolean end = false;
                StringBuilder dataString = new StringBuilder(length);
                int totalBytesRead = 0;
                //We need to run while loop, to read all data in that stream
                while(!end) {
                    int currentBytesRead = in.read(messageByte);
                    totalBytesRead = currentBytesRead + totalBytesRead;
                    if(totalBytesRead <= length) {
                        dataString.append(new String(messageByte,0,currentBytesRead,StandardCharsets.UTF_8));
                    } else {
                        dataString.append(new String(messageByte,0,length - totalBytesRead + currentBytesRead,StandardCharsets.UTF_8));
                    }
                    if(dataString.length()>=length) {
                        end = true;
                    }
                }
                System.out.println("Read "+length+" bytes of message from client. Message = "+dataString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}