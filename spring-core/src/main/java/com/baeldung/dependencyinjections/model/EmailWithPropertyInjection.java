package com.baeldung.dependencyinjections.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Email created as a result of property injection
 * 
 *  Equivalent XML config
 * <bean id="email" class="com.spring.demo.model.EmailWithSetterInjection">
   		<property name="sender" ref="sender" />
        <property name="rx" ref="receiver" />
        <property name="content" ref="content" />
   </bean>
 *  
 * @author hemant
 * @since 11-Feb-18
 *
 */
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
