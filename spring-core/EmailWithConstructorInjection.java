package com.spring.demo.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Email created as a result of Constructor injection
 * 
 * Equivalent XML config is
 * 
 *  <bean id="email" class="com.spring.demo.model.EmailWithConstructorInjection">
        <constructor-arg index="0" ref="sender"/>
        <constructor-arg index="1" ref="receiver"/>
        <constructor-arg index="2" ref="content"/>
    </bean>
 * 
 * @author hemant
 *
 */
@Component("email")
@Profile("ci")
public class EmailWithConstructorInjection implements Email {
	private static final Log LOG = LogFactory.getLog(EmailWithConstructorInjection.class);
	private Sender sender;
	private Receiver rx;
	private Content content;
	
	
	@Autowired
	public EmailWithConstructorInjection(Sender sender, Receiver receiver, Content content) {
		this.sender = sender;
		this.rx = receiver;
		this.content = content;
		LOG.info("Parameterized constructor called");
	}


	@Override
	public String toString() {
		return "EmailWithCI [sender=" + sender + ", rx=" + rx + ", content=" + content + "]";
	}

}
