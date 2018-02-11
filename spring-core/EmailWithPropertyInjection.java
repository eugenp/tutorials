package com.spring.demo.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component("email")
@Profile("pi")
public class EmailWithPropertyInjection implements Email {
	private static final Log LOG = LogFactory.getLog(EmailWithPropertyInjection.class);
	@Autowired
	private Sender sender;
	@Autowired
	private Receiver rx;
	@Autowired
	private Content content;
	
	public EmailWithPropertyInjection() {
		LOG.info("Default constructor called");
	}


	@Override
	public String toString() {
		return "EmailWithPropertyInjection [sender=" + sender + ", rx=" + rx + ", content=" + content + "]";
	}

}
