/**
 * 
 */
package com.juxtapose.example.ch05;

/**
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-3-25下午10:28:35
 */
public class MockBRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3841087497940611523L;
	public MockBRuntimeException(){
		super();
	}
	public MockBRuntimeException(String message){
		super(message);
	}
}
