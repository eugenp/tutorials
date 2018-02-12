package com.baeldung.dependencyinjections.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

/**
 * Model representing email receiver
 * @author hemant
 * @since 11-Feb-18
 */
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
