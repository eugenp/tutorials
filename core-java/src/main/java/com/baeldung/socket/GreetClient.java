package com.baeldung.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GreetClient {
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;

	public void startConnection(String ip, int port) {
		try {
			clientSocket = new Socket(ip, port);
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));
		} catch (IOException e) {
			
		}

	}

	public String sendMessage(String msg) {
		try {
			out.println(msg);
			String resp = in.readLine();
			return resp;
		} catch (Exception e) {
			return null;
		}
	}

	public void stopConnection() {
		try {
			in.close();
			out.close();
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
