package org.apache.camel.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class FileProcessor implements Processor {

	public void process(Exchange exchange) throws Exception {
		//Read file content
		String originalFileContent = (String)exchange.getIn().getBody(String.class);
		
		//Convert file content to upper case.
		String upperCaseFileContent = originalFileContent.toUpperCase();
		
		//Update file content.
		exchange.getIn().setBody(upperCaseFileContent);
	}

}
