package com.baeldung.dependencyinjections.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

/**
 * Model representing email sender
 * @author hemant
 * @since 11-Feb-18
 */
@Component
public class Sender {
	private static final Log LOG = LogFactory.getLog(Sender.class);
	private String id = "sender";

	public Sender() {
		LOG.info("Default constructor of SENDER called : " + this);
	}

	@Override
	public String toString() {
		return "Sender [id=" + id + "]";
	}

}
