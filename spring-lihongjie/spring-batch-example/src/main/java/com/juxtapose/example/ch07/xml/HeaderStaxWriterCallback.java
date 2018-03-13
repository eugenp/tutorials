/**
 * 
 */
package com.juxtapose.example.ch07.xml;

import java.io.IOException;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

import org.springframework.batch.item.xml.StaxWriterCallback;

/**
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-9-21下午03:47:06
 */
public class HeaderStaxWriterCallback implements StaxWriterCallback {

	/* (non-Javadoc)
	 * @see org.springframework.batch.item.xml.StaxWriterCallback#write(javax.xml.stream.XMLEventWriter)
	 */
	@Override
	public void write(XMLEventWriter writer) throws IOException {
		XMLEventFactory factory = XMLEventFactory.newInstance();
		try {
			writer.add(factory.createComment("credit 201310 begin."));
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
	}

}
