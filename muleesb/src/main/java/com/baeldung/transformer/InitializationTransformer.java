package com.baeldung.transformer;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.api.transport.PropertyScope;
import org.mule.transformer.AbstractMessageTransformer;

public class InitializationTransformer extends AbstractMessageTransformer {

	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
		// TODO Auto-generated method stub

		String payload = null;

		try {
			payload = message.getPayloadAsString();
		   }catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Logged Payload: "+payload);
		message.setPayload("Payload from Initialization");		
		message.setProperty("outboundKey", "outboundpropertyvalue",PropertyScope.OUTBOUND);
		
		
		return message;
	}
}