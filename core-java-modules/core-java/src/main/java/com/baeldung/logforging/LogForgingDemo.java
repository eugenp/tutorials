package com.baeldung.logforging;

import org.owasp.esapi.ESAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogForgingDemo {

	private final Logger logger = LoggerFactory.getLogger(LogForgingDemo.class);

	public void addLog(String amount) {
		logger.info("Amount credited = {}", amount);
	}

	public static void main(String[] args) {
		LogForgingDemo demo = new LogForgingDemo();
		demo.addLog("300");
		demo.addLog("300 \n\nweb - 2017-04-12 17:47:08,957 [main] INFO Amount reversed successfully");
		demo.addLog(encode("300 \n\nweb - 2017-04-12 17:47:08,957 [main] INFO Amount reversed successfully"));
	}

	public static String encode(String message) {
		message = message.replace('\n', '_').replace('\r', '_').replace('\t', '_');
		message = ESAPI.encoder().encodeForHTML(message);
		return message;
	}

}
