package com.concertosoft;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.concertosoft.VO.PayerVO;

@RestController

public class NPCIController {
	
	private static ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(10);
	
	
	@GetMapping("/NPCI")
	public String getHome(Model model) {
		return "NPCI";
	}
	
	
//	@PostMapping(value ="ReqPay" , consumes = MediaType.APPLICATION_XML_VALUE)
	@PostMapping("ReqPay")
	public String requestPay(@RequestBody String requestDetails, Model model) {
		model.addAttribute("ack", "acknowledgement from NPCI");
		System.out.println(requestDetails);
		try {
		ScheduledFuture response = threadPool.schedule(new MyThread("http://localhost:8080/NPCISimulator/RespPay"), 1000l, TimeUnit.MILLISECONDS);

		}catch(Exception e) {e.printStackTrace();}
		System.out.println(" from req pay");
		return "acknowledge";
	}
	
	class MyThread implements Callable{
		
		String url = new String();
		BufferedReader in = null;
		public MyThread(String url) {
			this.url = url;
		}
		
		public String call() {
			StringBuffer response = new StringBuffer();
			try {
			URL obj = new URL(url);
	//		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			// add reuqest header
	//		con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
	//		con.setRequestProperty("Content-Type", "application/xml");
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

			// Send post request
			con.setDoOutput(true);
		

			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			

		
				while ((inputLine = in.readLine()) != null) {
	
					response.append(inputLine);
		
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
			
			}
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response.toString();

			
		}
		
	}
	

}
