package com.baeldung.transformer;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;

public class InvokingMessageComponent extends AbstractMessageTransformer {

	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
		// TODO Auto-generated method stub	
		String InboundProp = (String) message.getInboundProperty("outboundKey");
		System.out.println("InboundProp:" + InboundProp);		
		return InboundProp;
	}
}