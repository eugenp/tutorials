package com.baeldung;

import java.util.Date;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class MyPayloadClonePrepare implements Processor {

	public void process(Exchange exchange) throws Exception {
		MyPayload myPayload = exchange.getIn().getBody(MyPayload.class);
		exchange.getIn().setBody(myPayload.deepClone());
		exchange.getIn().setHeader("date", new Date());
	}
}
