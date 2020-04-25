package com.baeldung.sample.adpater;

import com.baeldung.sample.port.Article;

//dummy class
public class WebPublisher {

	public static void publish(Article article) {
		//publish content to online server here
	}
	
	public static double checkOnlineForPlagiarismScore(String content) {
		return 20.00;
		
	}
	
	public static double checkOnlineForFactScore(String content) {
		return 90.00;
	}

}
