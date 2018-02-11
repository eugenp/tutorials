package com.spring.demo.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

@Component
public class Content {
	private static final Log LOG = LogFactory.getLog(Content.class);
	private String subject = "subject";
	private String body = "***BODY***";

	public Content() {
		LOG.info("Default constructor of CONTENT called : " + this);
	}

	@Override
	public String toString() {
		return "Content [subject=" + subject + ", body=" + body + "]";
	}

}
