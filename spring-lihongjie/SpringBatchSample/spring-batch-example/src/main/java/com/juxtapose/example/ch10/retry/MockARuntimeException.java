/**
 * 
 */
package com.juxtapose.example.ch10.retry;

/**
 * 
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-10-21下午10:09:35
 */
public class MockARuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3841087497940611523L;
	public MockARuntimeException(){
		super();
	}
	public MockARuntimeException(String message){
		super(message);
	}
}
