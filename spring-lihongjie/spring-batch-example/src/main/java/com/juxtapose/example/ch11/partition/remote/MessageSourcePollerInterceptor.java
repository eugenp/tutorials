/**
 * 
 */
package com.juxtapose.example.ch11.partition.remote;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.channel.interceptor.ChannelInterceptorAdapter;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.util.Assert;

/**
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2014-3-23下午03:36:20
 */
public class MessageSourcePollerInterceptor extends ChannelInterceptorAdapter implements InitializingBean{

	private static Log logger = LogFactory.getLog(MessageSourcePollerInterceptor.class);

	private MessageSource<?> source;

	private MessageChannel channel;

	public MessageSourcePollerInterceptor() {
	}

	public MessageSourcePollerInterceptor(MessageSource<?> source) {
		this.source = source;
	}

	public void setChannel(MessageChannel channel) {
		this.channel = channel;
	}

	public void afterPropertiesSet() throws Exception {
		Assert.state(source != null, "A MessageSource must be provided");
	}

	public void setMessageSource(MessageSource<?> source) {
		this.source = source;
	}

	@Override
	public boolean preReceive(MessageChannel channel) {
		Message<?> message = source.receive();
		Collection<StepExecution> collections = new ArrayList<StepExecution>();
		while(message != null){
			collections.add((StepExecution)message.getPayload());
			message = source.receive();
		}
		if (this.channel != null) {
			channel = this.channel;
		}
		channel.send(MessageBuilder.withPayload(collections).build());
		if (logger.isDebugEnabled()) {
			logger.debug("Sent " + message + " to channel " + channel);
		}
		return true;
	}

}
