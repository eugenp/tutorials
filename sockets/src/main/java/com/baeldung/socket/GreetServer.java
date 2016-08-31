package com.baeldung.socket;

import java.net.*;
import java.io.*;

public class GreetServer {
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;


	public void start(int port) {
		try {
			serverSocket = new ServerSocket(port);
			clientSocket = serverSocket.accept();
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));
			String greeting = in.readLine();
			if ("hello server".equals(greeting))
				out.println("hello client");
			else
				out.println("unrecognised greeting");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void stop() {
		try {
			in.close();
			out.close();
			clientSocket.close();
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public static void main(String[] args) {
		GreetServer server=new GreetServer();
		server.start(6666);
	}

}
