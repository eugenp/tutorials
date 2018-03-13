/**
 * 
 */
package com.juxtapose.example.ch06.flat;

import org.springframework.batch.item.file.LineCallbackHandler;

/**
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-4-3上午09:41:08
 */
public class DefaultLineCallbackHandler implements LineCallbackHandler {

	/* (non-Javadoc)
	 * @see org.springframework.batch.item.file.LineCallbackHandler#handleLine(java.lang.String)
	 */
	public void handleLine(String line) {
		System.out.println("Skipped line content:" + line);
	}

}
