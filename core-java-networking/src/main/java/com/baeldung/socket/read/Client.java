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
        //Read 100 bytes of data from console and send to server
        String data = "";
        try {
            data = in.readLine();
            out.writeUTF(data);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    public static void main(String[] args) {
        // Start Client
        Client client = new Client();
        client.runClient("127.0.0.1", 5555);
    }

}
