package com.baeldung.dependencyinjections.model;

/**
 * Generic interface representing email
 * @author hemant
 * @since 11-Feb-18
 */
public interface Email {

	Receiver getReceiver();

	Sender getSender();

	Content getContent();

}
