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
            byte[] messageByte = new byte[1000];
            boolean end = false;
            String dataString = "";
            int totalBytesRead = 0;
            //We need to run while loop, to read all data in that stream
            while(!end) {
                int currentBytesRead = in.read(messageByte);
                totalBytesRead = currentBytesRead + totalBytesRead;
                if(totalBytesRead <= 10) {
                    dataString += new String(messageByte,0,currentBytesRead);   
                } else {
                    dataString += new String(messageByte,0,10 - totalBytesRead + currentBytesRead); 
                }
                if(dataString.length()>=10) {
                    end = true;
                }
            }
            System.out.println("Read 10 bytes of message from client. Message = "+dataString);;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        //Run Server
        Server server = new Server();
        server.runServer(5555);
    }

}