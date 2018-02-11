package com.spring.demo.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

@Component
public class Receiver {
	private static final Log LOG = LogFactory.getLog(Receiver.class);
	private String id = "receiver";

	public Receiver() {
		LOG.info("Default constructor of RECEIVER called : " + this);
	}

	@Override
	public String toString() {
		return "Receiver [id=" + id + "]";
	}

}
