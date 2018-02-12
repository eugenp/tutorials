package com.baeldung.dependencyinjections.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


/**
 * Email created as a result of setter injection
 * 
 * Equivalent XML config
 * 
 * <bean id="email" class="com.spring.demo.model.EmailWithSetterInjection">
   		<property name="sender" ref="sender" />
        <property name="rx" ref="receiver" />
        <property name="content" ref="content" />
   </bean>
 * @author hemant
 *
 */
@Component("email")
@Profile("si")
public class EmailWithSetterInjection implements Email {
	private static final Log LOG = LogFactory.getLog(EmailWithSetterInjection.class);
	private Sender sender;
	private Receiver rx;
	private Content content;
	
	public EmailWithSetterInjection() {
		LOG.info("Default constructor called");
	}

	@Autowired
	public void setContent(Content content) {
		LOG.info("Setter of content called with " + content);
		this.content = content;
	}
	
	@Autowired
	public void setSender(Sender sender) {
		LOG.info("Setter of sender called with " + sender);
		this.sender = sender;
	}
	
	@Autowired
	public void setRx(Receiver rx) {
		LOG.info("Setter of receiver called with " + rx);
		this.rx = rx;
	}
	

	@Override
	public String toString() {
		return "EmailWithSetterInjection [sender=" + sender + ", rx=" + rx + ", content=" + content + "]";
	}
	
	@Override
	public Content getContent() {
		return content;
	}

	@Override
	public Receiver getReceiver() {
		return rx;
	}

	@Override
	public Sender getSender() {
		return sender;
	}

}
