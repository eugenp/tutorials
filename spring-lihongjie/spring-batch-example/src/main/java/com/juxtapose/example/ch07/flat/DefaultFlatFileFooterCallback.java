/**
 * 
 */
package com.juxtapose.example.ch07.flat;

import java.io.IOException;
import java.io.Writer;

import org.springframework.batch.item.file.FlatFileFooterCallback;

/**
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-9-20上午09:14:05
 */
public class DefaultFlatFileFooterCallback implements FlatFileFooterCallback {

	/* (non-Javadoc)
	 * @see org.springframework.batch.item.file.FlatFileFooterCallback#writeFooter(java.io.Writer)
	 */
	@Override
	public void writeFooter(Writer writer) throws IOException {
		writer.write("##credit 201310 end.");
	}

}
